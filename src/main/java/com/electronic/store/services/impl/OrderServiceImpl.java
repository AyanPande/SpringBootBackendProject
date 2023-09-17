package com.electronic.store.services.impl;

import com.electronic.store.dtos.CreateOrderRequest;
import com.electronic.store.dtos.OrderConfirmationDto;
import com.electronic.store.dtos.OrderDto;
import com.electronic.store.entities.*;
import com.electronic.store.enums.OrderStatus;
import com.electronic.store.enums.PaymentStatus;
import com.electronic.store.exceptions.BadApiRequestException;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.repositories.CartRepository;
import com.electronic.store.repositories.OrderRepository;
import com.electronic.store.repositories.UserRepository;
import com.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user);

        Integer total = 0;

        if (Objects.isNull(cart)) {
            throw new ResourceNotFoundException("Cart not found for : " + user.getName());
        }
        List<CartItem> cartItemList = cart.getCartItemList();

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setBillingAddress(orderDto.getBillingAddress());
        order.setBillingPhoneNumber(orderDto.getBillingPhoneNumber());
        order.setBillingName(orderDto.getBillingName());
        order.setOrderDate(Instant.now());
        order.setDeliveredDate(orderDto.getDeliveredDate());
        order.setUser(user);

        List<OrderItem> orderItemList = new ArrayList<>();


        if (!ObjectUtils.isEmpty(cartItemList)) {
            for (CartItem items : cartItemList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(items.getQuantity());
                orderItem.setTotalPrice(items.getTotalPrice());
                orderItem.setProduct(items.getProduct());
                orderItem.setOrder(order);
                orderItemList.add(orderItem);
            }
        }

        order.setOrderItems(orderItemList);

        for (OrderItem orderItem : orderItemList) {
            total = total + orderItem.getTotalPrice();
        }

        order.setTotalBill(total);

        cart.getCartItemList().clear();
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public List<OrderDto> findOrderByUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!!"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(modelMapper.map(order, OrderDto.class));
        }

        return orderDtos;
    }

    @Override
    public List<OrderDto> findAllOrder() {

        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(modelMapper.map(order, OrderDto.class));
        }

        return orderDtos;
    }

    @Override
    public OrderDto updateStatusOfOrder(OrderConfirmationDto orderConfirmationDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!!"));
        Order order = orderRepository.updateStatusOfOrder(user.getUserId());

        order.setOrderStatus(orderConfirmationDto.getOrderStatus());
        order.setPaymentStatus(orderConfirmationDto.getPaymentStatus());
        order.setDeliveredDate(Instant.now());

        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDto.class);

    }
}