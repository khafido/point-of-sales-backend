package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.entity.SupplierEntity;
import com.mitrais.cdcpos.dto.SupplierDto;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/supplier")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<GenericResponse> listSuppliers(
            @RequestParam(defaultValue = "false") Boolean isPaginated,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        try {
            Page<SupplierEntity> items = supplierService.listSuppliers(isPaginated, page, size, searchValue, sortBy, sortDirection);

            List<SupplierDto> listDto = items.getContent().stream()
                    .map(SupplierDto::toDto)
                    .collect(Collectors.toList());

            PaginatedDto<SupplierDto> result = new PaginatedDto<>(
                    listDto,
                    items.getNumber(),
                    items.getTotalPages()
            );
            return new ResponseEntity<>(new GenericResponse(result, "Get Suppliers Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse> addSupplier(
            @RequestBody SupplierDto reqBody
    ) {
        try {
            SupplierEntity result = supplierService.addSupplier(reqBody);
            SupplierDto resultDto = SupplierDto.toDto(result);
            return new ResponseEntity<>(new GenericResponse(resultDto, "Add Suppliers Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> updateSupplier(
            @PathVariable UUID id,
            @RequestBody SupplierDto reqBody
    ) {
        try {
            SupplierEntity result = supplierService.updateSupplier(id, reqBody);
            if(result!=null) {
                SupplierDto resultDto = SupplierDto.toDto(result);
                return new ResponseEntity<>(new GenericResponse(resultDto, "Update Suppliers Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new GenericResponse(null, "Suppler ID Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteSupplier(
            @PathVariable UUID id
    ) {
        try {
            SupplierEntity result = supplierService.deleteSupplier(id);
            if(result!=null) {
                SupplierDto resultDto = SupplierDto.toDto(result);
                return new ResponseEntity<>(new GenericResponse(resultDto, "Delete Suppliers Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new GenericResponse(null, "Suppler ID Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
