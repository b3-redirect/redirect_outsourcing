package com.sparta.redirect_outsourcing.domain.restaurant.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "restaurants")
@Entity
@NoArgsConstructor
public class Restaurant extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RestaurntCategoryEnum category;

    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

//    public Restaurant(RestaurantCreateRequestDto createReq) {
//        this.name = createReq.getName();
//        this.address = createReq.getAddress();
//        this.category = createReq.getCategory();
//        this.description = createReq.getDescription();
//    }


//    public void update(RestaurantUpdateRequestDto updateReq) {
//        this.name = updateReq.getName();
//        this.address = updateReq.getAddress();
//        this.category = updateReq.getCategory();
//        this.description = updateReq.getDescription();
//    }
}