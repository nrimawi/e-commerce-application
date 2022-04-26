package edu.birzeit.webservices.webservicesfirstassignment.controller;


import edu.birzeit.webservices.webservicesfirstassignment.dto.CategoryDto;
import edu.birzeit.webservices.webservicesfirstassignment.exception.BadRequestException;
import edu.birzeit.webservices.webservicesfirstassignment.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.birzeit.webservices.webservicesfirstassignment.service.CategoryService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Autowired
    private CategoryService CategoryService; //the use of interface rather than class is important for loose coupling

    // Constructor based  injection
    public CategoryResource(CategoryService CategoryService) {
        this.CategoryService = CategoryService;
    }

    @GetMapping
    public ResponseEntity getAllCategories() {
        try {
            return ResponseEntity.ok().body(CategoryService.getAllCategories()); //ResponseEntity represents an HTTP response, including headers, body, and status.

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity getCategoryById(@PathVariable(name = "id") long id) {
        try {
            return ResponseEntity.ok(CategoryService.getCategoryById(id));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping
    public ResponseEntity createCategory(@Valid @RequestBody CategoryDto CategoryDto) {
        try {


            if (CategoryDto.getId() != null) {
                log.error("Cannot have an ID {}", CategoryDto);
                throw new BadRequestException(CategoryResource.class.getSimpleName(),
                        "Id");
            }

            return new ResponseEntity<>(CategoryService.createCategory(CategoryDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@Valid @RequestBody CategoryDto CategoryDto
            , @PathVariable(name = "id") long id) {

        try {

            return new ResponseEntity<>(CategoryService.updateCategory(CategoryDto, id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") long id) {
        try {

            CategoryService.deleteCategoryById(id);
            return new ResponseEntity<>("Category with id="+id+" has been deleted successfully.", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
