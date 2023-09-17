package com.electronic.store.dtos;

import com.electronic.store.entities.Order;
import com.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Integer quantity;
    private Integer totalPrice;
    private Product product;
}
