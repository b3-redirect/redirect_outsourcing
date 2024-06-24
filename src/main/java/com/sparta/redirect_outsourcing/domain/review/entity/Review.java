package com.sparta.redirect_outsourcing.domain.review.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "reviews")
@Entity
@NoArgsConstructor
public class Review extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Float rating;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurants_id")
    private Restaurant restaurant;

    public Review(Float rating, String comment,User user , Restaurant restaurant) {
        this.rating = rating;
        this.comment = comment;
        this.likeCount = 0;
        this.user = user;
        this.restaurant = restaurant;
    }

    public void update(ReviewRequestDto requestDto){
        this.rating = requestDto.getRating();
        this.comment = requestDto.getComment();
    }
}