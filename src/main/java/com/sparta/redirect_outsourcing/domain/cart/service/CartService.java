package com.sparta.redirect_outsourcing.domain.cart.service;

import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemRequestDto;
import com.sparta.redirect_outsourcing.domain.cart.dto.CartItemResponseDto;
import com.sparta.redirect_outsourcing.domain.cart.entity.Cart;
import com.sparta.redirect_outsourcing.domain.cart.entity.CartItem;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartAdapter;
import com.sparta.redirect_outsourcing.domain.cart.repository.CartItemAdapter;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartAdapter cartAdapter;
    private final CartItemAdapter cartItemAdapter;
    private final UserAdapter userAdapter;
    private final MenuAdapter menuAdapter;

    public CartItemResponseDto addItemToCart(CartItemRequestDto requestDto) {
        Cart cart = cartAdapter.findById(requestDto.getCartId())
            .orElseGet(() -> cartAdapter.save(new Cart(requestDto.getCartId(), requestDto.getUsersId())));

        Menu menu = menuAdapter.findById(requestDto.getMenusId())
            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        CartItem cartItem = new CartItem(cart, menu, requestDto.getQuantity(),
            requestDto.getQuantityPrice());
        cartItem = cartItemAdapter.save(cartItem);

        return new CartItemResponseDto(cartItem);
    }

    public List<CartItemResponseDto> getCartItems() {
        List<CartItem> cartItems = cartItemAdapter.findAll();
        return cartItems.stream().map(this::toDto).collect(Collectors.toList());
    }

    public CartItemResponseDto updateCartItem(CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemAdapter.findById(requestDto.getId());

        Cart cart = cartAdapter.findById(requestDto.getCartId());

        Menu menu = menuAdapter.findById(requestDto.getMenusId())
            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        cartItem.update(cart, menu, requestDto.getQuantity(), requestDto.getQuantityPrice());
        cartItem = cartItemAdapter.save(cartItem);

        return toDto(cartItem);
    }

    public void deleteCartItems(List<Long> menuIds) {
        cartItemAdapter.deleteAllByMenuIdIn(menuIds);
    }

    private  CartItemResponseDto toDto(CartItem cartItem) {
        return new CartItemResponseDto(
            cartItem.getId(),
            cartItem.getCart().getId(),
            cartItem.getMenu().getId(),
            cartItem.getQuantity(),
            cartItem.getQuantityPrice()
        );
    }
}
