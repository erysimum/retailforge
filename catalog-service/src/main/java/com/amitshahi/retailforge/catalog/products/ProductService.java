package com.amitshahi.retailforge.catalog.products;

import com.amitshahi.retailforge.catalog.config.ApplicationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.spel.ReactiveEvaluationContextProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ApplicationProperties properties;
    private final ProductRepository productRepository;

    public ProductService(ApplicationProperties properties, ProductRepository productRepository) {
        this.properties = properties;
        this.productRepository = productRepository;
    }

//    public List<Product> getProducts() {
//        return productRepository.findAll().stream().map(ProductMapper::toDTO).toList();
//    }
     public PagedResult<Product> getProducts(int pageNo){
        int pageIndex = pageNo -1;
         Pageable pageable = PageRequest.of(
                 pageIndex,
                 properties.pageSize(),
                 Sort.by("id").ascending()
         );
         Page<Product> page = productRepository
                 .findAll(pageable)
                 .map(ProductMapper::toDTO);
         int reportedPage = page.getTotalPages() == 0 ? 1 : Math.min(pageNo, page.getTotalPages());
         return new PagedResult<>(
                 page.getContent(),
                 page.getTotalElements(),
                 //page.getNumber() + 1,
                 reportedPage,
                 page.getTotalPages(),
                 page.isFirst(),
                 page.isLast(),
                 page.hasNext(),
                 page.hasPrevious()
         );

     }

    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .map(ProductMapper::toDTO)
                .orElseThrow(() -> ProductNotFoundException.withSku(sku));
    }
}

