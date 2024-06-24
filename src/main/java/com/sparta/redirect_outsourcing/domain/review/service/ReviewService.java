package com.sparta.redirect_outsourcing.domain.review.service;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.review.ReviewOverRatingException;
import com.sparta.redirect_outsourcing.exception.custom.user.UserNotMatchException;
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
    private final RestaurantAdapter restaurantAdapter;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto requestDto , User user){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }
        Restaurant restaurant = restaurantAdapter.findById(requestDto.getRestaurantId());
        Review review = new Review(requestDto.getRating(), requestDto.getComment(),user , restaurant);
        Review savedReview = reviewAdapter.save(review);
        return ReviewResponseDto.of(savedReview);
    }

    @Transactional
    public ReviewResponseDto updateReview(ReviewRequestDto requestDto , Long reviewId , User user){
        Review review = reviewAdapter.findById(reviewId);
        if(review.getUser().getId() != user.getId() ){
            throw new UserNotMatchException(ResponseCodeEnum.MENU_USER_NOT_MATCH);
        }
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }

        review.update(requestDto);
        return ReviewResponseDto.of(review);
    }

    @Transactional
    public void deleteReview(Long reviewId , User user){
        Review review = reviewAdapter.findById(reviewId);
        if(review.getUser().getId() != user.getId() ){
            throw new UserNotMatchException(ResponseCodeEnum.MENU_USER_NOT_MATCH);
        }
        reviewAdapter.delete(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviews(Long restaurantId){
        List<Review> reviews = reviewAdapter.findByRestaurantId(restaurantId);
        List<ReviewResponseDto> responseReviews = new ArrayList<>();
        for (Review review : reviews) {
            responseReviews.add(ReviewResponseDto.of(review));
        }
        return responseReviews;
    }

}