package com.electronic.store.controllers;

import com.electronic.store.dtos.*;
import com.electronic.store.services.FileService;
import com.electronic.store.services.ProductService;
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

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.profile.image.path}")
    private String productImageUploadPath;

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
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId") String productId) throws IOException {
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

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponseMessage> uploadProductCoverImage(@RequestParam("productImage") MultipartFile productImage, @PathVariable("productId") String productId) throws IOException {
        String imageName = fileService.uploadFile(productImage, productImageUploadPath);
        ProductDto productDto = productService.getSingleProduct(productId);
        productDto.setProductImage(imageName);
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        ImageResponseMessage imageResponseMessage = ImageResponseMessage.builder().imageName(imageName).imagePath(productImageUploadPath).message("Image Uploaded").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageResponseMessage, HttpStatus.OK);
    }

    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable("productId") String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getSingleProduct(productId);
        InputStream resource = fileService.getResource(productImageUploadPath, productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<ProductDto> createProductWithCategory(@Valid @RequestBody ProductDto productDto, @PathVariable("categoryId") String categoryId){
        ProductDto productDtoWithCategory = productService.createProductWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productDtoWithCategory,HttpStatus.CREATED);
    }
}