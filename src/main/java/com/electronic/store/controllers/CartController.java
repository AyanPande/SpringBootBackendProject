package com.electronic.store.controllers;

import com.electronic.store.dtos.AddItemToCartDto;
import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.CartDto;
import com.electronic.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<CartDto> createCart(@PathVariable("userId") String userId, @RequestBody AddItemToCartDto addItemToCartDto) {
        CartDto cartDto = cartService.addItemToCart(userId, addItemToCartDto);
        return new ResponseEntity<>(cartDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{userId}/{cartItemId}")
    public ResponseEntity<ApiResponseMessage> deleteProductByUser(
            @PathVariable("userId") String userId,
            @PathVariable("cartItemId") int cartItemId
    )
    {
        cartService.removeItemFromCart(userId,cartItemId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Deleted!!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCartForUser(@PathVariable("userId") String userId) {
        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .success(true)
                .message("Cart cleared for userId: " + userId)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
