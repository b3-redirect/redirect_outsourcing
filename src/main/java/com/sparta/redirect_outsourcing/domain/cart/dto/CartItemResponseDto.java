package com.sparta.redirect_outsourcing.domain.cart.dto;

import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemResponseDto {
    private Long id;
    private Long cartId;
    private Long menusId;
    private Long quantity;
    private Long quantityPrice;

    public CartItemResponseDto(Long id, Long cartId, Long menusId, Long quantity, Long quantityPrice) {
    }

    public CartItemResponseDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.cartId = cartItem.getCart().getId();
        this.menusId = cartItem.getMenu().getId();
        this.quantity = cartItem.getQuantity();
        this.quantityPrice = cartItem.getQuantityPrice();
    }
}
