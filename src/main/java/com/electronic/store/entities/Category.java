package com.electronic.store.entities;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "category_title", nullable = false)
    private String categoryTitle;
    @Column(name = "category_description")
    private String categoryDescription;
    @Column(name = "category_cover_image")
    private String categoryCoverImage;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
    private List<Product> productList;
}