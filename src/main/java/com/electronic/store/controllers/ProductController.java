package com.electronic.store.controllers;

import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto savedProductDto = productService.createProduct(productDto);
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") String productId,@Valid @RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProductDto,HttpStatus.OK);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteProduct(productId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Product Deleted").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable("productId") String productId) {
        ProductDto singleProductDto = productService.getSingleProduct(productId);
        return new ResponseEntity<>(singleProductDto,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }
    @GetMapping("/liveProduct")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> allLiveProduct = productService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allLiveProduct,HttpStatus.OK);
    }

    @GetMapping("/search/{productTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductByTitle(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable("productTitle") String productTitle) {
        PageableResponse<ProductDto> searchedProductDto = productService.searchProductByTitle(pageNumber, pageSize, sortBy, sortDir, productTitle);
        return new ResponseEntity<>(searchedProductDto,HttpStatus.OK);
    }
}
