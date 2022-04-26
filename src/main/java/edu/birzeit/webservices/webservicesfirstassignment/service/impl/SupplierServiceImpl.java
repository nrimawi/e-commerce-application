package edu.birzeit.webservices.webservicesfirstassignment.service.impl;


import edu.birzeit.webservices.webservicesfirstassignment.dto.SupplierDto;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Supplier;
import edu.birzeit.webservices.webservicesfirstassignment.exception.ResourceNotFoundException;
import edu.birzeit.webservices.webservicesfirstassignment.repository.SupplierRepository;
import edu.birzeit.webservices.webservicesfirstassignment.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //To enable this class for component scanning
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository SupplierRepository;

    public SupplierServiceImpl(edu.birzeit.webservices.webservicesfirstassignment.repository.SupplierRepository SupplierRepository) {
        this.SupplierRepository = SupplierRepository;
    }

    @Override
    public SupplierDto createSupplier(SupplierDto SupplierDto) {

        try {
            // convert DTO to entity
            Supplier Supplier = mapToEntity(SupplierDto);
            Supplier.setIsActive(true);
            Supplier newSupplier = SupplierRepository.save(Supplier);

            // convert entity to DTO
            SupplierDto SupplierResponse = mapToDTO(newSupplier);
            return SupplierResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        try {

            List<Supplier> categories = SupplierRepository.findAll();
            ArrayList<SupplierDto> categoriesDto = new ArrayList<SupplierDto>();

            for (Supplier Supplier : categories)
                if (Supplier.getIsActive())
                    categoriesDto.add(mapToDTO(Supplier));

            return categoriesDto;
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public SupplierDto getSupplierById(long id) {
        try {
            Supplier Supplier = SupplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
            if (!Supplier.getIsActive())
                throw new ResourceNotFoundException("Supplier", "id", id);
            return mapToDTO(Supplier);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto SupplierDto, long id) {
        try {
            // get Supplier by id from the database
            Supplier Supplier = SupplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
            if (Supplier.getIsActive()) {
                SupplierDto.setId(id);

                Supplier = mapToEntity(SupplierDto);
                Supplier.setIsActive(true);

            }
            Supplier updatedSupplier = SupplierRepository.save(Supplier);

            return mapToDTO(updatedSupplier);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteSupplierById(long id) {
        try {
            // get Supplier by id from the database
            Supplier Supplier = SupplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
            if (!Supplier.getIsActive())
                throw new ResourceNotFoundException("Supplier", "id", id);
            Supplier.setIsActive(false);
            SupplierRepository.save(Supplier);
        } catch (Exception e) {
            throw e;
        }
    }

    private SupplierDto mapToDTO(Supplier Supplier) {
        try {

            SupplierDto SupplierDto = new SupplierDto();
            SupplierDto.setName(Supplier.getName());
            SupplierDto.setId(Supplier.getId());
            SupplierDto.setContactName(Supplier.getContactName());
            SupplierDto.setEmail(Supplier.getEmail());
            SupplierDto.setPhone(Supplier.getPhone());
            SupplierDto.setAddress(Supplier.getAddress());
            return SupplierDto;
        } catch (Exception e) {
            throw e;
        }
    }

    private Supplier mapToEntity(SupplierDto SupplierDto) {
        try {
            Supplier Supplier = new Supplier();
            Supplier.setId(SupplierDto.getId());
            Supplier.setName(SupplierDto.getName());
            Supplier.setContactName(SupplierDto.getContactName());
            Supplier.setEmail(SupplierDto.getEmail());
            Supplier.setPhone(SupplierDto.getPhone());
            Supplier.setAddress(SupplierDto.getAddress());
            return Supplier;
        } catch (Exception e) {
            throw e;
        }
    }
}