package com.electronic.store.dtos;

import com.electronic.store.entities.OrderItem;
import com.electronic.store.entities.User;
import com.electronic.store.enums.OrderStatus;
import com.electronic.store.enums.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String orderId;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;
    private String billingAddress;
    private String billingPhoneNumber;
    private String billingName;
    private Instant orderDate;
    private Instant deliveredDate;
    private Integer totalBill;
    private User user;
    private List<OrderItem> orderItems;
}
