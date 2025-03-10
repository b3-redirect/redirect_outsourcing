package com.sparta.redirect_outsourcing.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.user.dto.LoginRequestDto;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.entity.UserStatusEnum;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import com.sparta.redirect_outsourcing.exception.custom.user.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j(topic = "로그인 처리및 JWT 생성 하여 알맞게 처리")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtProvider;
    private final UserAdapter userAdapter;
    private final ObjectMapper objectMapper;


    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserAdapter userAdapter, ObjectMapper objectMapper) {
        this.jwtProvider = jwtProvider;
        this.userAdapter = userAdapter;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/users/login");
    }

    // 로그인 정보 추출 및 인증 시도 (해당 메서드에서 예외가 발생하지 않으면 Spring Security에서 자동으로 successfulAuthentication를 실행시키고 예외가 발생하면 unsuccessfulAuthentication를 실행시킨다)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter 시작");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            User user = userAdapter.findByUsername(requestDto.getUsername());

            // 탈퇴한 사용자인지 확인
            if (user.getUserStatus() == UserStatusEnum.STATUS_DELETED) {
                throw new UserException(ResponseCodeEnum.USER_DELETED);
            }

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 인증 성공 처리
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("JwtAuthenticationFilter: 인증 성공");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String accessToken = jwtProvider.createAccessToken(username);
        String refreshToken = jwtProvider.createRefreshToken(username);

        // 응답 헤더에 Access Token만 담기
        response.addHeader(JwtProvider.AUTHORIZATION_HEADER, accessToken);

        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        user.setRefreshToken(refreshToken);
        userAdapter.save(user);  // Refresh Token은 유저 필드에 저장

        response.setStatus(HttpServletResponse.SC_OK);

        // 로그인 성공 응답 본문 설정
        ResponseEntity<MessageResponseDto> responseEntity = ResponseUtils.of(HttpStatus.OK, "로그인 성공");
        writeResponseBody(response, responseEntity);
    }

    // 인증 실패 처리
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("JwtAuthenticationFilter: 인증 실패");
        request.setAttribute("loginSuccess", false); // 로그인 실패 플래그 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 로그인 실패 응답 본문 설정
        writeResponseBody(response, ResponseUtils.of(HttpStatus.BAD_REQUEST, "로그인 실패"));
    }

    // 응답을 헤더뿐아니라 바디에도 추가로 보내주기위한 메서드
    private void writeResponseBody(HttpServletResponse response, ResponseEntity<MessageResponseDto> responseEntity) throws IOException {
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            objectMapper.writeValue(outputStream, responseEntity.getBody());
            outputStream.flush();
        }
    }
}
