package com.sparta.redirect_outsourcing.domain.cart.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.user.UserException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartAdapter {
    private final CartRepository cartRepository;

    public Cart findById(Long id) {
        return cartRepository.findById(id)
            .orElseThrow(() -> new UserException(ResponseCodeEnum.CART_NOT_FOUND));
    }

    public List<CartItem> findAllByUsersId(Long usersId) {
        return cartRepository.findAllByUsersId(usersId);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}


