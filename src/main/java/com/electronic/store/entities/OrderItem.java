package com.electronic.store.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrice;

    @OneToOne
    @JoinColumn(name = "product_id")
    private  Product product;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private  Order order;
}
