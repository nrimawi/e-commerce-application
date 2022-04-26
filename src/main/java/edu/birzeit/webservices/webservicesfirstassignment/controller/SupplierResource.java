package edu.birzeit.webservices.webservicesfirstassignment.controller;


import edu.birzeit.webservices.webservicesfirstassignment.dto.SupplierDto;
import edu.birzeit.webservices.webservicesfirstassignment.exception.BadRequestException;
import edu.birzeit.webservices.webservicesfirstassignment.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.birzeit.webservices.webservicesfirstassignment.service.SupplierService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/suppliers")
public class SupplierResource {
    private final Logger log = LoggerFactory.getLogger(SupplierResource.class);

    @Autowired
    private SupplierService SupplierService; //the use of interface rather than class is important for loose coupling

    // Constructor based  injection
    public SupplierResource(SupplierService SupplierService) {
        this.SupplierService = SupplierService;
    }

    @GetMapping
    public ResponseEntity getAllsuppliers() {
        try {
            return ResponseEntity.ok().body(SupplierService.getAllSuppliers()); //ResponseEntity represents an HTTP response, including headers, body, and status.


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity getSupplierById(@PathVariable(name = "id") long id) {
        try {
            return ResponseEntity.ok(SupplierService.getSupplierById(id));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping
    public ResponseEntity createSupplier(@Valid @RequestBody SupplierDto SupplierDto) {
        try {


            if (SupplierDto.getId() != null) {
                log.error("Cannot have an ID {}", SupplierDto);
                throw new BadRequestException(SupplierResource.class.getSimpleName(),
                        "Id");
            }

            return new ResponseEntity<>(SupplierService.createSupplier(SupplierDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity updateSupplier(@Valid @RequestBody SupplierDto SupplierDto
            , @PathVariable(name = "id") long id) {

        try {

            return new ResponseEntity<>(SupplierService.updateSupplier(SupplierDto, id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable(name = "id") long id) {
        try {

            SupplierService.deleteSupplierById(id);
            return new ResponseEntity<>("Supplier with id="+id+" has been deleted successfully.", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
