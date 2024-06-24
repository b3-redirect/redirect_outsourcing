package com.sparta.redirect_outsourcing.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
public class ReviewRequestDto {
    @NotBlank(message = "점수를 입력해주세요.")
    private Float rating;
    @NotBlank(message = "리뷰를 입력해주세요.")
    private String comment;

    private Long restaurantId;
}
