package com.electronic.store.services;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;

import java.io.IOException;

public interface ProductService {
    //create
    public ProductDto createProduct(ProductDto productDto);
    //update
    public ProductDto updateProduct(ProductDto productDto, String productId);
    //delete
    public void deleteProduct(String productId) throws IOException;
    //getSingleProduct
    public ProductDto getSingleProduct(String productId);
    //getAllProduct
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    //getAllLiveProduct
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    //searchProductByTitle
    public PageableResponse<ProductDto> searchProductByTitle(int pageNumber, int pageSize, String sortBy, String sortDir, String keywords);
    //create product for a particular category
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId);
    //update existing product with category
    public ProductDto updateProductWithCategory(String productId, String categoryId);
    //find products for a particular category
    public PageableResponse<ProductDto> findProductByCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
}