package com.electronic.store.dtos;

import com.electronic.store.enums.OrderStatus;
import com.electronic.store.enums.PaymentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
    @NotBlank(message = "Cart id is required !!")
    private String cartId;
    @NotBlank(message = "Cart id is required !!")
    private String userId;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;
    @NotBlank(message = "Address is required !!")
    private String billingAddress;
    @NotBlank(message = "Phone number is required !!")
    private String billingPhone;
    @NotBlank(message = "Billing name  is required !!")
    private String billingName;
}
