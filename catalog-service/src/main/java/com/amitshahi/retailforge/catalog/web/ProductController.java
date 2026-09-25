package com.amitshahi.retailforge.catalog.web;

import com.amitshahi.retailforge.catalog.products.PagedResult;
import com.amitshahi.retailforge.catalog.products.Product;
import com.amitshahi.retailforge.catalog.products.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int page) {
        return productService.getProducts(page);
    }

    @GetMapping("/{sku}")
    Product getProductBySku(@PathVariable("sku") String sku) {
        return productService.getProductBySku(sku);
    }
}
