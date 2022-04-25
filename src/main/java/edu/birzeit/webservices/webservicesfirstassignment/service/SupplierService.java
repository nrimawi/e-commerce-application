package edu.birzeit.webservices.webservicesfirstassignment.service;


import edu.birzeit.webservices.webservicesfirstassignment.dto.SupplierDto;

import java.util.List;

public interface SupplierService {
    SupplierDto createSupplier(SupplierDto SupplierDto);

    List<SupplierDto> getAllSuppliers();

    SupplierDto getSupplierById(long id);

    SupplierDto updateSupplier(SupplierDto SupplierDto, long id);

    void deleteSupplierById(long id);
}
