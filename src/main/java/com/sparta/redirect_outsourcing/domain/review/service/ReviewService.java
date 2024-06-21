package com.sparta.redirect_outsourcing.domain.review.service;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.review.ReviewOverRatingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewAdapter reviewAdapter;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto , UserDetailsImpl userDetails){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }
        User user = userDetails.getUser();
        Review review = new Review(requestDto.getRating(), requestDto.getComment(),user);
        Review savedReview = reviewAdapter.save(review);
        return ReviewResponseDto.of(savedReview);
    }

    @Transactional
    public ReviewResponseDto updateReview(ReviewRequestDto requestDto , Long reviewId){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }
        Review review = reviewAdapter.findById(reviewId);
        review.update(requestDto);
        return ReviewResponseDto.of(review);
    }

    @Transactional
    public void deleteReview(Long reviewId){
        Review review = reviewAdapter.findById(reviewId);
        reviewAdapter.delete(review);
    }

    //싣당 부분 완성후 수정할 예정
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviews(){
        List<Review> reviews = reviewAdapter.findAll();
        List<ReviewResponseDto> responseReviews = new ArrayList<>();
        for (Review review : reviews) {
            responseReviews.add(ReviewResponseDto.of(review));
        }
        return responseReviews;
    }

}