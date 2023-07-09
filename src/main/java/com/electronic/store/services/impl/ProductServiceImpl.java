package com.electronic.store.services.impl;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.entities.Category;
import com.electronic.store.entities.Product;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.repositories.CategoryRepository;
import com.electronic.store.repositories.ProductRepository;
import com.electronic.store.services.ProductService;
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
import java.time.Instant;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Value("${product.profile.image.path}")
    private String productImageUploadPath;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        Product product = dtoToEntity(productDto);
        product.setProductId(productId);
        product.setAddedDate(Instant.now());
        product.setUpdatedDate(Instant.now());
        Product savedProduct = productRepository.save(product);
        ProductDto savedProductDto = entityToDto(savedProduct);
        return savedProductDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        product.setProductTitle(productDto.getProductTitle());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductDiscountedPrice(productDto.getProductDiscountedPrice());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setProductLive(productDto.isProductLive());
        product.setProductStock(productDto.isProductStock());
        product.setUpdatedDate(Instant.now());
        product.setProductImage(productDto.getProductImage());
        Product updatedProduct = productRepository.save(product);
        ProductDto updatedProductDto = entityToDto(updatedProduct);
        return updatedProductDto;
    }

    @Override
    public void deleteProduct(String productId) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found!!"));
        String fullPath = productImageUploadPath  + product.getProductImage();
        Path path = Paths.get(fullPath);
        Files.delete(path);
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found!!"));
        Category category = product.getCategory();
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        ProductDto productDto = entityToDto(product);
        productDto.setCategoryDto(categoryDto);
        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByProductLiveTrue(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> searchProductByTitle(int pageNumber, int pageSize, String sortBy, String sortDir, String keywords) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByProductTitleContaining(pageable,keywords);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found for particular categoryId"));
        Product product = dtoToEntity(productDto);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(Instant.now());
        product.setUpdatedDate(Instant.now());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        ProductDto savedProductDto = entityToDto(savedProduct);
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        savedProductDto.setCategoryDto(categoryDto);
        return savedProductDto;
    }

    public Product dtoToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    public ProductDto entityToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}