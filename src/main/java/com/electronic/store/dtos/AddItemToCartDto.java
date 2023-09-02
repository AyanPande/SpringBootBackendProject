package com.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartDto {

    private String productId;

    private int quantity;
}
