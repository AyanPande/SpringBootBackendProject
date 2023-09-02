package com.electronic.store.repositories;

import com.electronic.store.entities.Cart;
import com.electronic.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    List<CartItem> findAllByCart(Cart cart);
}
