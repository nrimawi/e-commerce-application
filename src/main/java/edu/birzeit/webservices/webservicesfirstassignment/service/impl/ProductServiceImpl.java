package edu.birzeit.webservices.webservicesfirstassignment.service.impl;



import edu.birzeit.webservices.webservicesfirstassignment.dto.ProductDto;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Product;
import edu.birzeit.webservices.webservicesfirstassignment.exception.ResourceNotFoundException;
import edu.birzeit.webservices.webservicesfirstassignment.repository.ProductRepository;
import edu.birzeit.webservices.webservicesfirstassignment.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //To enable this class for component scanning
public class ProductServiceImpl implements ProductService {

    private ProductRepository ProductRepository;

    public ProductServiceImpl(edu.birzeit.webservices.webservicesfirstassignment.repository.ProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto ProductDto) {

        Product Product = mapToEntity(ProductDto);
        Product newProduct = ProductRepository.save(Product);

        ProductDto ProductResponse = mapToDTO(newProduct);
        return ProductResponse;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = ProductRepository.findAll();
        ArrayList<ProductDto> productsDto=new ArrayList<ProductDto>();

        for (Product  Product:products)
            if(Product.getIsActive())
                productsDto.add(mapToDTO(Product));

        return  productsDto;
    }

    @Override
    public ProductDto getProductById(long id) {
        Product Product = ProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        if (!Product.getIsActive())
            throw new ResourceNotFoundException("Product", "id", id);
        return mapToDTO(Product);
    }

    @Override
    public ProductDto updateProduct(ProductDto ProductDto, long id) {
        // get Product by id from the database
        Product Product = ProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        if(Product.getIsActive()) {
            Product.setName(ProductDto.getName());
            Product.setDescription(ProductDto.getDescription());
            Product.setCreationDate(ProductDto.getCreationDate());
        }
        Product updatedProduct = ProductRepository.save(Product);

        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(long id) {
        // get Product by id from the database
        Product Product = ProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        Product.setIsActive(false);
        ProductRepository.save(Product);
    }

    private ProductDto mapToDTO(Product Product){
        ProductDto ProductDto = new ProductDto();
        ProductDto.setId(Product.getId());
        ProductDto.setCreationDate(Product.getCreationDate());
        ProductDto.setDescription(Product.getDescription());
        ProductDto.setName(Product.getName());
        ProductDto.setPrice(Product.getPrice());
        return ProductDto;
    }

    private Product mapToEntity(ProductDto ProductDto){
        Product Product = new Product();
        Product.setId(ProductDto.getId());
        Product.setCreationDate(ProductDto.getCreationDate());
        Product.setDescription(ProductDto.getDescription());
        Product.setName(ProductDto.getName());
        Product.setPrice(ProductDto.getPrice());

        return Product;
    }
}
