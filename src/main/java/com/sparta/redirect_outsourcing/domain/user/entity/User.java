package com.sparta.redirect_outsourcing.domain.user.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String introduce;

    @Column
    private String pictureUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private UserStatusEnum userStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private UserRoleEnum userRole;

    @Column
    private String refreshToken;

    @Column
    private String kakaoId;

    @ElementCollection
    @Column
    private List<String> previousPasswords = new ArrayList<>();
}