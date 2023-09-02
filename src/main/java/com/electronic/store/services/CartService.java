package com.electronic.store.services;

import com.electronic.store.dtos.AddItemToCartDto;
import com.electronic.store.dtos.CartDto;
import org.springframework.stereotype.Service;

public interface CartService {

    public CartDto addItemToCart(String userId, AddItemToCartDto addItemToCartDto);

    public void removeItemFromCart(String userId, int cartItemId);

    public void clearCart(String userId);
}
