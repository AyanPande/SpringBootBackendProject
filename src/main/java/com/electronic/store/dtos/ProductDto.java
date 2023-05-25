package com.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private String productId;
    @NotBlank(message = "product title is required")
    private String productTitle;
    @NotBlank(message = "product description is required")
    private String productDescription;
    @NotBlank(message = "product price is required")
    private int productPrice;
    @NotBlank(message = "discounted price is required")
    private int productDiscountedPrice;
    @NotBlank(message = "product quantity is required")
    private int productQuantity;
    private Date addedDate;
    @NotBlank(message = "product live is required")
    private boolean productLive;
    @NotBlank(message = "product stock is required")
    private boolean productStock;
}
