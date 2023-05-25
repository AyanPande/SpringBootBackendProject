package com.electronic.store.services.impl;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entities.Category;
import com.electronic.store.entities.User;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.repositories.CategoryRepository;
import com.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Value("${category.profile.image.path}")
    private String categoryImageUploadPath;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category categoryEntity = dtoToEntity(categoryDto);
        Category savedCategoryEntity = categoryRepository.save(categoryEntity);
        CategoryDto savedCategoryDto = entityToDto(savedCategoryEntity);
        return savedCategoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!"));
        categoryEntity.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryEntity.setCategoryTitle(categoryDto.getCategoryTitle());
        categoryEntity.setCategoryCoverImage(categoryDto.getCategoryCoverImage());
        Category updatedCategoryEntity = categoryRepository.save(categoryEntity);
        CategoryDto updatedCategoryDto = entityToDto(updatedCategoryEntity);
        return updatedCategoryDto;
    }

    @Override
    public void deleteCategory(String categoryId) throws IOException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        String fullPath = categoryImageUploadPath  + category.getCategoryCoverImage();
        Path path = Paths.get(fullPath);
        Files.delete(path);
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
        return response;
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category  categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!!"));
        CategoryDto categoryDto = entityToDto(categoryEntity);
        return categoryDto;
    }

    @Override
    public PageableResponse<CategoryDto> searchCategory(int pageNumber, int pageSize, String sortBy, String sortDir,String keywords) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findByCategoryTitleContaining(pageable,keywords);
        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
        return response;
    }


    public CategoryDto entityToDto(Category category) {
        return modelMapper.map(category,CategoryDto.class);
    }

    public Category dtoToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
