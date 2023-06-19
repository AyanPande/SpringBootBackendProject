package com.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
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
    private int productPrice;
    private int productDiscountedPrice;
    private int productQuantity;
    private Instant addedDate;
    private Instant updatedDate;
    private boolean productLive;
    private boolean productStock;
    private String productImage;
}
