package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.SupplierRequestDto;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Page<SupplierEntity> listSuppliers (
            boolean paginated,
            int page,
            int size,
            String searchValue,
            String sortBy,
            String sortDirection) {

        Sort sort;
        Pageable paging;
        Page<SupplierEntity> result;

        if("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if(paginated) {
            paging = PageRequest.of(page, size, sort);
            result = supplierRepository.findAllSearch(paging, searchValue);
        } else {
            List<SupplierEntity> entityList = supplierRepository.findAllSearch(sort, searchValue);
            result = new PageImpl<>(entityList);
        }
        return result;
    }

    public SupplierEntity addSupplier (SupplierRequestDto request) {

        SupplierEntity entity = new SupplierEntity();
        entity.setName(request.getName());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setAddress(request.getAddress());
        entity.setCpname(request.getCpname());

        return supplierRepository.save(entity);
    }

    public SupplierEntity updateSupplier (UUID id, SupplierRequestDto request) {
        Optional<SupplierEntity> optional = supplierRepository.findById(id);

        if(optional.isPresent()) {
            SupplierEntity entity = optional.get();
            entity.setName(request.getName());
            entity.setPhone(request.getPhone());
            entity.setEmail(request.getEmail());
            entity.setAddress(request.getAddress());
            entity.setCpname(request.getCpname());

            return supplierRepository.save(entity);
        }
        return null;
    }

    public SupplierEntity deleteSupplier (UUID id) {
        Optional<SupplierEntity> optional = supplierRepository.findById(id);

        if(optional.isPresent()) {
            SupplierEntity entity = optional.get();
            entity.setDeletedAt(LocalDateTime.now());

            return supplierRepository.save(entity);
        }
        return null;
    }


}
