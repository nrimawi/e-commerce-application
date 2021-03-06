package edu.birzeit.webservices.webservicesfirstassignment.service.impl;


import edu.birzeit.webservices.webservicesfirstassignment.dto.CategoryDto;
import edu.birzeit.webservices.webservicesfirstassignment.dto.ProductDto;
import edu.birzeit.webservices.webservicesfirstassignment.dto.SupplierDto;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Category;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Product;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Supplier;
import edu.birzeit.webservices.webservicesfirstassignment.exception.ResourceNotFoundException;
import edu.birzeit.webservices.webservicesfirstassignment.repository.ProductRepository;
import edu.birzeit.webservices.webservicesfirstassignment.repository.SupplierRepository;
import edu.birzeit.webservices.webservicesfirstassignment.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //To enable this class for component scanning
public class ProductServiceImpl implements ProductService {

    private ProductRepository ProductRepository;

    public ProductServiceImpl(edu.birzeit.webservices.webservicesfirstassignment.repository.ProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto ProductDto) {

        try {
            // convert DTO to entity
            Product Product = mapToEntity(ProductDto);
            Product.setIsActive(true);
            Product newProduct = ProductRepository.save(Product);

            // convert entity to DTO
            ProductDto ProductResponse = mapToDTO(newProduct);
            return ProductResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        try {

            List<Product> categories = ProductRepository.findAll();
            ArrayList<ProductDto> categoriesDto = new ArrayList<ProductDto>();

            for (Product Product : categories)
                if (Product.getIsActive())
                    categoriesDto.add(mapToDTO(Product));

            return categoriesDto;
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public ProductDto getProductById(long id) {
        try {
            Product Product = ProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            if (!Product.getIsActive())
                throw new ResourceNotFoundException("Product", "id", id);
            return mapToDTO(Product);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ProductDto updateProduct(ProductDto ProductDto, long id) {
        try {
            // get Product by id from the database
            Product Product = ProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            if (Product.getIsActive()) {
                ProductDto.setId(id);
                Product = mapToEntity(ProductDto);
                Product.setIsActive(true);


            }
            Product updatedProduct = ProductRepository.save(Product);

            return mapToDTO(updatedProduct);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteProductById(long id) {
        try {
            // get Product by id from the database
            Product Product = ProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            if (!Product.getIsActive())
                throw new ResourceNotFoundException("Product", "id", id);
            Product.setIsActive(false);
            ProductRepository.save(Product);
        } catch (Exception e) {
            throw e;
        }
    }

    private ProductDto mapToDTO(Product Product) {
        try {

            ProductDto ProductDto = new ProductDto();
            ProductDto.setName(Product.getName());
            ProductDto.setId(Product.getId());
            ProductDto.setPrice(Product.getPrice());
            ProductDto.setQuantity(Product.getQuantity());
            ProductDto.setDescription(Product.getDescription());

            SupplierDto supplierDto = new SupplierDto();
            supplierDto.setId(Product.getSupplier().getId());
            ProductDto.setSupplierId(Product.getSupplier().getId());

            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(Product.getCategory().getId());
            ProductDto.setCategoryId(Product.getCategory().getId());

            return ProductDto;
        } catch (Exception e) {
            throw e;
        }
    }

    private Product mapToEntity(ProductDto ProductDto) {
        try {
            Product Product = new Product();
            Product.setName(ProductDto.getName());
            Product.setId(ProductDto.getId());
            Product.setPrice(ProductDto.getPrice());
            Product.setQuantity(ProductDto.getQuantity());
            Product.setDescription(ProductDto.getDescription());


            Supplier supplier = new Supplier();
            supplier.setId(ProductDto.getSupplierId());
            Product.setSupplier(supplier);

            Category category = new Category();
            category.setId(ProductDto.getCategoryId());
            Product.setCategory(category);

            return Product;
        } catch (Exception e) {
            throw e;
        }
    }
}