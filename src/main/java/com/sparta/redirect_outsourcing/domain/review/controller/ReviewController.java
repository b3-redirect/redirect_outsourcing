package com.sparta.redirect_outsourcing.domain.review.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/restaurants/reviews")
    public ResponseEntity<DataResponseDto<ReviewResponseDto>> createReview(
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        ReviewResponseDto responseDto = reviewService.createReview(requestDto,userDetails);
        return ResponseUtils.of(HttpStatus.OK, "리뷰 작성 성공",responseDto);
    }

    @PutMapping("/restaurants/reviews/{reviewId}")
    public ResponseEntity<DataResponseDto<ReviewResponseDto>> updateReview(
            @RequestBody ReviewRequestDto requestDto , @PathVariable Long reviewId){
        ReviewResponseDto responseDto = reviewService.updateReview(requestDto,reviewId);
        return ResponseUtils.of(HttpStatus.OK,"리뷰 수정 성공",responseDto);
    }

    @DeleteMapping("/restaurants/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseUtils.of(HttpStatus.OK,"리뷰 삭제 성공");
    }

    //Todo 음식점기능 완료되면 해당 음식점의 리뷰를 불러올 수 있도록 해야 됨
    @GetMapping("/restaurants/reviews")
    public ResponseEntity<DataResponseDto<List<ReviewResponseDto>>>getReviewList(){
        List<ReviewResponseDto> reviews = reviewService.getReviews();
        return ResponseUtils.of(HttpStatus.OK,"리뷰 조회 성공",reviews);
    }


}