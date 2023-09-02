package com.electronic.store.dtos;

import com.electronic.store.entities.CartItem;
import com.electronic.store.entities.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Instant createdAt;
    private User user;
    private List<CartItemDto> cartItemDtoList = new ArrayList<>();
}
