package com.sparta.redirect_outsourcing.domain.menu.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor
public class Menu extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private MenuCategoryEnum menuCategoryEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurants_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public Menu(String name , int price , MenuCategoryEnum menuCategoryEnum){
        this.name = name;
        this.price = price;
        this.menuCategoryEnum = menuCategoryEnum;
    }

    public void update(String name , int price , MenuCategoryEnum menuCategoryEnum){
        this.name = name;
        this.price = price;
        this.menuCategoryEnum = menuCategoryEnum;
    }
}