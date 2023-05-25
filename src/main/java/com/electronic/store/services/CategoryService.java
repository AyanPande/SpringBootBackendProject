package com.electronic.store.services;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    //create
    public CategoryDto createCategory(CategoryDto categoryDto);
    //update

    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    //delete

    public void deleteCategory(String categoryId) throws IOException;

    //getAll

    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single category
    public CategoryDto getCategoryById(String categoryId);

    //search category
    public PageableResponse<CategoryDto> searchCategory(int pageNumber, int pageSize, String sortBy, String sortDir,String keywords);

}
