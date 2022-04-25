package edu.birzeit.webservices.webservicesfirstassignment.service;


import edu.birzeit.webservices.webservicesfirstassignment.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto ProductDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(long id);

    ProductDto updateProduct(ProductDto ProductDto, long id);

    void deleteProductById(long id);
}
