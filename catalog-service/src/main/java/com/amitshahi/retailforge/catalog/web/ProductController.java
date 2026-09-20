package com.amitshahi.retailforge.catalog.web;

import com.amitshahi.retailforge.catalog.products.Product;
import com.amitshahi.retailforge.catalog.products.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    List<Product> getProducts() {
        return productService.getProducts();
    }
}
