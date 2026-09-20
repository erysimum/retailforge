package com.amitshahi.retailforge.catalog.products;
 class ProductMapper {
    static Product toDTO(ProductEntity entity){
        return new Product(entity.getSku(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice());
    }
}
