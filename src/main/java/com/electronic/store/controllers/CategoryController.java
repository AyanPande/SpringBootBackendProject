package com.electronic.store.controllers;

import com.electronic.store.dtos.*;
import com.electronic.store.services.CategoryService;
import com.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Value("${category.profile.image.path}")
    private String categoryImageUploadPath;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") String categoryId) {
        CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategoryDto,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId) throws IOException {
        categoryService.deleteCategory(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("category deleted successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<CategoryDto> categoryListDto = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(categoryListDto, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> findSingleCategory(@PathVariable("categoryId") String categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponseMessage> uploadCategoryCoverImage(@RequestParam("categoryImage") MultipartFile categoryImage, @PathVariable("categoryId") String categoryId) throws IOException {
        String imageName = fileService.uploadFile(categoryImage, categoryImageUploadPath);
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        categoryDto.setCategoryCoverImage(imageName);
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        ImageResponseMessage imageResponseMessage = ImageResponseMessage.builder().imageName(imageName).imagePath(categoryImageUploadPath).message("Image Uploaded").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageResponseMessage, HttpStatus.OK);
    }

    @GetMapping("/image/{categoryId}")
    public void serveUserImage(@PathVariable("categoryId") String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        logger.info("File name {}", categoryDto.getCategoryCoverImage());
        InputStream resource = fileService.getResource(categoryImageUploadPath, categoryDto.getCategoryCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<PageableResponse<CategoryDto>> searchCategoryByTitle(
            @PathVariable("keywords") String keywords,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<CategoryDto> filteredCategoryDto = categoryService.searchCategory(pageNumber,pageSize,sortBy,sortDir,keywords);
        return new ResponseEntity<>(filteredCategoryDto,HttpStatus.OK);
    }
}
