package com.sparta.redirect_outsourcing.domain.cart.repository;

import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import com.sparta.redirect_outsourcing.exception.custom.user.UserException;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartItemAdapter {
    private final CartItemRepository cartItemRepository;

    public CartItem findById(Long id) {
        return cartItemRepository.findById(id)
            .orElseThrow(() -> new UserException(ResponseCodeEnum.CART_ITEM_NOT_FOUND));
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteAllByMenuIdIn(List<Long> menuIds) {
        cartItemRepository.deleteAllByMenuIdIn(menuIds);
    }
}
