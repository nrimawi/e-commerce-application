package edu.birzeit.webservices.webservicesfirstassignment.service.impl;



import edu.birzeit.webservices.webservicesfirstassignment.dto.SupplierDto;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Supplier;
import edu.birzeit.webservices.webservicesfirstassignment.exception.ResourceNotFoundException;
import edu.birzeit.webservices.webservicesfirstassignment.repository.SupplierRepository;
import edu.birzeit.webservices.webservicesfirstassignment.service.SupplierService;
import edu.birzeit.webservices.webservicesfirstassignment.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //To enable this class for component scanning
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository SupplierRepository;

    public SupplierServiceImpl(edu.birzeit.webservices.webservicesfirstassignment.repository.SupplierRepository SupplierRepository) {
        this.SupplierRepository = SupplierRepository;
    }

    @Override
    public SupplierDto createSupplier(SupplierDto SupplierDto) {

        Supplier Supplier = mapToEntity(SupplierDto);
        Supplier newSupplier = SupplierRepository.save(Supplier);

        SupplierDto SupplierResponse = mapToDTO(newSupplier);
        return SupplierResponse;
    }

    @Override
        public List<SupplierDto> getAllSuppliers() {
        List<Supplier> suppliers = SupplierRepository.findAll();
        ArrayList<SupplierDto> suppliersDto=new ArrayList<SupplierDto>();

        for (Supplier  Supplier:suppliers)
            if(Supplier.getIsActive())
                suppliersDto.add(mapToDTO(Supplier));

        return  suppliersDto;
    }



    @Override
    public SupplierDto getSupplierById(long id) {
        Supplier Supplier = SupplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
        if (!Supplier.getIsActive())
            throw new ResourceNotFoundException("Supplier", "id", id);
        return mapToDTO(Supplier);
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto SupplierDto, long id) {
        // get Supplier by id from the database
        Supplier Supplier = SupplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
        if(Supplier.getIsActive()) {
            Supplier.setName(SupplierDto.getName());
            Supplier.setCreationDate(SupplierDto.getCreationDate());
        }
        Supplier updatedSupplier = SupplierRepository.save(Supplier);

        return mapToDTO(updatedSupplier);
    }

    @Override
    public void deleteSupplierById(long id) {
        // get Supplier by id from the database
        Supplier Supplier = SupplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));
        Supplier.setIsActive(false);
        SupplierRepository.save(Supplier);
    }

    private SupplierDto mapToDTO(Supplier Supplier){
        SupplierDto SupplierDto = new SupplierDto();
        SupplierDto.setId(Supplier.getId());
        SupplierDto.setCreationDate(Supplier.getCreationDate());
        SupplierDto.setName(Supplier.getName());
        return SupplierDto;
    }

    private Supplier mapToEntity(SupplierDto SupplierDto){
        Supplier Supplier = new Supplier();
        Supplier.setId(SupplierDto.getId());
        Supplier.setCreationDate(SupplierDto.getCreationDate());
        Supplier.setName(SupplierDto.getName());
        return Supplier;
    }
}
