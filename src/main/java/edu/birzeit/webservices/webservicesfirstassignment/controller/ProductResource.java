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
import edu.birzeit.webservices.webservicesfirstassignment.service.ProductService;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ResponseEntity getAllProduct() {
        try {
            return ResponseEntity.ok().body(ProductService.getAllProducts()); //ResponseEntity represents an HTTP response, including headers, body, and status.

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable(name = "id") long id) {
        try {
            return ResponseEntity.ok(ProductService.getProductById(id));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping
    public ResponseEntity createProduct(@Valid @RequestBody ProductDto ProductDto) {
        try {


            if (ProductDto.getId() != null) {
                log.error("Cannot have an ID {}", ProductDto);
                throw new BadRequestException(ProductResource.class.getSimpleName(),
                        "Id");
            }

            return new ResponseEntity<>(ProductService.createProduct(ProductDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@Valid @RequestBody ProductDto ProductDto
            , @PathVariable(name = "id") long id) {

        try {

            return new ResponseEntity<>(ProductService.updateProduct(ProductDto, id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") long id) {
        try {

            ProductService.deleteProductById(id);
            return new ResponseEntity<>("Product with id="+id+" has been deleted successfully.", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
