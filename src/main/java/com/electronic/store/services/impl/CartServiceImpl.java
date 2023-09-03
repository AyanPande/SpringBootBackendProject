package com.electronic.store.services.impl;

import com.electronic.store.controllers.CategoryController;
import com.electronic.store.dtos.*;
import com.electronic.store.entities.Cart;
import com.electronic.store.entities.CartItem;
import com.electronic.store.entities.Product;
import com.electronic.store.entities.User;
import com.electronic.store.exceptions.BadApiRequestException;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.repositories.CartItemRepository;
import com.electronic.store.repositories.CartRepository;
import com.electronic.store.repositories.ProductRepository;
import com.electronic.store.repositories.UserRepository;
import com.electronic.store.services.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    Boolean updated = Boolean.FALSE;
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartDto addItemToCartDto) {

        int quantity = addItemToCartDto.getQuantity();
        String productId = addItemToCartDto.getProductId();

        if (quantity <= 0){
            throw new BadApiRequestException("Quantity should not be less than 1");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Cart cart = cartRepository.findByUser(user);

        if (Objects.isNull(cart)) {
            Cart newCart = new Cart();
            newCart.setCreatedAt(Instant.now());
            newCart.setCartId(UUID.randomUUID().toString());
            List<CartItem> cartItemList = newCart.getCartItemList();
            if (ObjectUtils.isEmpty(cartItemList)) {
                CartItem cartItem = CartItem.builder()
                        .product(product)
                        .quantity(quantity)
                        .totalPrice(product.getProductPrice() * quantity)
                        .cart(newCart)
                        .build();
                cartItemList.add(cartItem);
                newCart.setUser(user);
                Cart savedCart = cartRepository.save(newCart);
                CartDto savedCartDto = modelMapper.map(savedCart, CartDto.class);
                for (int i = 0; i < savedCart.getCartItemList().size(); i++) {
                    CartItemDto cartItemListDto = modelMapper.map(savedCart.getCartItemList().get(i), CartItemDto.class);
                    savedCartDto.getCartItemDtoList().add(cartItemListDto);
                    ProductDto productDto = modelMapper.map(savedCart.getCartItemList().get(i).getProduct(), ProductDto.class);
                    savedCartDto.getCartItemDtoList().get(i).setProductDto(productDto);
                    CategoryDto categoryDto = modelMapper.map(savedCart.getCartItemList().get(i).getProduct().getCategory(), CategoryDto.class);
                    savedCartDto.getCartItemDtoList().get(i).getProductDto().setCategoryDto(categoryDto);
                }
                return savedCartDto;
            }
        } else {
            List<CartItem> cartItemList = cart.getCartItemList();
            cartItemList = cartItemList.stream().map(item -> {
                if (item.getProduct().getProductId().equals(productId)) {
                    item.setQuantity(quantity);
                    item.setTotalPrice(product.getProductPrice() * quantity);
                    updated = Boolean.TRUE;
                }
                return item;
            }).collect(Collectors.toList());

            if (!updated) {
                List<CartItem> cartItems = cart.getCartItemList();
                CartItem cartItem = CartItem.builder()
                        .product(product)
                        .quantity(quantity)
                        .totalPrice(product.getProductPrice() * quantity)
                        .cart(cart)
                        .build();
                cartItems.add(cartItem);
                Cart saved = cartRepository.save(cart);
                CartDto cartDto = modelMapper.map(saved, CartDto.class);
                for (int i = 0; i < saved.getCartItemList().size(); i++){
                    CartItemDto cartItemListDto = modelMapper.map(saved.getCartItemList().get(i), CartItemDto.class);
                    cartDto.getCartItemDtoList().add(cartItemListDto);
                    ProductDto productDto = modelMapper.map(saved.getCartItemList().get(i).getProduct(), ProductDto.class);
                    cartDto.getCartItemDtoList().get(i).setProductDto(productDto);
                    CategoryDto categoryDto = modelMapper.map(saved.getCartItemList().get(i).getProduct().getCategory(), CategoryDto.class);
                    cartDto.getCartItemDtoList().get(i).getProductDto().setCategoryDto(categoryDto);
                }
                return cartDto;
            } else {
                Cart saved1 = cartRepository.save(cart);
                CartDto cartDto = modelMapper.map(saved1, CartDto.class);
                for (int i = 0; i < saved1.getCartItemList().size(); i++){
                    CartItemDto cartItemListDto = modelMapper.map(saved1.getCartItemList().get(i), CartItemDto.class);
                    cartDto.getCartItemDtoList().add(cartItemListDto);
                    ProductDto productDto = modelMapper.map(saved1.getCartItemList().get(i).getProduct(), ProductDto.class);
                    cartDto.getCartItemDtoList().get(i).setProductDto(productDto);
                    CategoryDto categoryDto = modelMapper.map(saved1.getCartItemList().get(i).getProduct().getCategory(), CategoryDto.class);
                    cartDto.getCartItemDtoList().get(i).getProductDto().setCategoryDto(categoryDto);
                }
                return cartDto;
            }
        }
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user);
        for (int i = 0; i < cart.getCartItemList().size(); i++) {
            if (cart.getCartItemList().get(i).getCartItemId() == cartItemId){
                cart.getCartItemList().remove(i);
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user);
        if (Objects.isNull(cart)){
            throw new ResourceNotFoundException("Cart not found");
        }
        cart.getCartItemList().clear();
        cartRepository.save(cart);

    }
}
