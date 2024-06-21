package com.sparta.redirect_outsourcing.domain.cart.entity;

import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "carts")
public class Cart extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long usersId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    // 카트 : 카트아이템 = 1:N 카트->카트아이템 단방향 관계 (cart_id)

    public Cart(Long id) {
        this.id = id;
    }
}