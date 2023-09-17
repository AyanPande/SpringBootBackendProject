package com.electronic.store.services;

import com.electronic.store.dtos.CreateOrderRequest;
import com.electronic.store.dtos.OrderConfirmationDto;
import com.electronic.store.dtos.OrderDto;

import java.util.List;

public interface OrderService {

    public OrderDto createOrder(OrderDto orderDto, String userId);

    public List<OrderDto> findOrderByUser(String userId);

    public List<OrderDto> findAllOrder();

    public OrderDto updateStatusOfOrder(OrderConfirmationDto orderConfirmationDto, String userId);

}
