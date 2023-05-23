package com.electronic.store.dtos;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String categoryId;
    @Size(min = 1, message = "Category title is required")
    private String categoryTitle;
    @NotBlank(message = "Category description required")
    private String categoryDescription;
    @NotBlank(message = "Cover Image Required")
    private String categoryCoverImage;
}