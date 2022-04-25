package edu.birzeit.webservices.webservicesfirstassignment.controller;



import edu.birzeit.webservices.webservicesfirstassignment.dto.ProductDto;
import edu.birzeit.webservices.webservicesfirstassignment.exception.BadRequestException;
import edu.birzeit.webservices.webservicesfirstassignment.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductResource {
    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

   @Autowired
    private ProductService ProductService; //the use of interface rather than class is important for loose coupling

// Constructor based  injection
    public ProductResource(ProductService ProductService) {
        this.ProductService = ProductService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllCategories() {
        return ResponseEntity.ok().body(ProductService.getAllProducts()); //ResponseEntity represents an HTTP response, including headers, body, and status.
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(ProductService.getProductById(id));
    }


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto ProductDto) {
        if (ProductDto.getId() != null) {
            log.error("Cannot have an ID {}", ProductDto);
            throw new BadRequestException(ProductResource.class.getSimpleName(),
                    "Id");
        }
        return new ResponseEntity<>(ProductService.createProduct(ProductDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto ProductDto
            , @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(ProductService.updateProduct(ProductDto, id), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") long id) {
        ProductService.deleteProductById(id);
//        return ResponseEntity.ok().headers(<add warnings....>).build();
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }
}
