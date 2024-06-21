package com.sparta.redirect_outsourcing.domain.order.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private Long orderGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Builder
    public Order(String address, String phoneNumber, String menuName, int price, int quantity, String restaurantName, Long orderGroup, User user) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
        this.restaurantName = restaurantName;
        this.orderGroup = orderGroup;
        this.user = user;
    }
}