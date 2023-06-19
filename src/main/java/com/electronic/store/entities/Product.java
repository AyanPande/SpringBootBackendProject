package com.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "product_id")
    private String productId;
    @Column(name = "product_title")
    private String productTitle;
    @Column(name = "product_description", length = 100000)
    private String productDescription;
    @Column(name = "product_price")
    private int productPrice;
    @Column(name = "product_discounted_price")
    private int productDiscountedPrice;
    @Column(name = "product_quantity")
    private int productQuantity;
    @Column(name = "added_date")
    private Instant addedDate;
    @Column(name = "updated_date")
    private Instant updatedDate;
    @Column(name = "product_live")
    private boolean productLive;
    @Column(name = "product_stock")
    private boolean productStock;
    @Column(name = "product_image")
    private String productImage;
}
