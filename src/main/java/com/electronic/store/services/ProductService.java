package com.electronic.store.services;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;

public interface ProductService {
    //create
    public ProductDto createProduct(ProductDto productDto);
    //update
    public ProductDto updateProduct(ProductDto productDto, String productId);
    //delete
    public void deleteProduct(String productId);
    //getSingleProduct
    public ProductDto getSingleProduct(String productId);
    //getAllProduct
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    //getAllLiveProduct
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    //searchProductByTitle
    public PageableResponse<ProductDto> searchProductByTitle(int pageNumber, int pageSize, String sortBy, String sortDir, String keywords);
}