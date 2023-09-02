package com.electronic.store.dtos;

import com.electronic.store.entities.Cart;
import com.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;
    private ProductDto productDto;
    private int quantity;
    private int totalPrice;
}
