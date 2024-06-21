package com.sparta.redirect_outsourcing.domain.cart.dto;

import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import lombok.Builder;
import lombok.Getter;
// 어떤 값을 전달해줄거니?
@Getter
@Builder
public class CartResponseDto {

    private Long userId;

    public static CartResponseDto of(Cart cart) {
        return CartResponseDto.builder()
            .userId(cart.getUsersId())
            .build();
    }

}
