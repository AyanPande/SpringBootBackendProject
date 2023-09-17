package com.electronic.store.dtos;

import com.electronic.store.enums.OrderStatus;
import com.electronic.store.enums.PaymentStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfirmationDto {

    private Instant deliveredDate;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;

}
