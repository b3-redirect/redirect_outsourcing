package com.sparta.redirect_outsourcing.domain.cart.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long quantityPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carts_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menus_id", nullable = false)
    private Menu menu;

    public CartItem(Cart cart /*Menu menu*/, Long quantity, Long quantityPrice) {
        this.cart = cart;
//        this.menu = menu;
        this.quantity = quantity;
        this.quantityPrice = quantityPrice;
    }

    public void update(Cart cart, Long quantity, Long quantityPrice) {
        this.cart = cart;
//        this.menu = menu;
        this.quantity = quantity;
        this.quantityPrice = quantityPrice;
    }
}