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

import javax.validation.Valid;
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
    public ResponseEntity<List<SupplierDto>> getAllCategories() {
        return ResponseEntity.ok().body(SupplierService.getAllSuppliers()); //ResponseEntity represents an HTTP response, including headers, body, and status.
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(SupplierService.getSupplierById(id));
    }


    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@Valid @RequestBody SupplierDto SupplierDto) {
        if (SupplierDto.getId() != null) {
            log.error("Cannot have an ID {}", SupplierDto);
            throw new BadRequestException(SupplierResource.class.getSimpleName(),
                    "Id");
        }
        return new ResponseEntity<>(SupplierService.createSupplier(SupplierDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@Valid @RequestBody SupplierDto SupplierDto
            , @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(SupplierService.updateSupplier(SupplierDto, id), HttpStatus.OK);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable(name = "id") long id) {
        SupplierService.deleteSupplierById(id);
//        return ResponseEntity.ok().headers(<add warnings....>).build();
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }
}
