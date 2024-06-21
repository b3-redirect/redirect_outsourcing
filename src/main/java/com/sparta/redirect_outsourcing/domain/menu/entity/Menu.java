package com.sparta.redirect_outsourcing.domain.menu.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
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
}