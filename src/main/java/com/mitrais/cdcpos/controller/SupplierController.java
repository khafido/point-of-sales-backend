package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.dto.SupplierRequestDto;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.service.SupplierService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@AllArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<GenericResponse> listSuppliers(
            @RequestParam(defaultValue = "false") Boolean isPaginated,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        try {
            Page<SupplierEntity> items = supplierService.listSuppliers(isPaginated, page, size, searchValue, sortBy, sortDirection);

            PaginatedDto<SupplierEntity> result = new PaginatedDto<>(
                    items.getContent(),
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
            @RequestBody SupplierRequestDto reqBody
    ) {
        try {
            SupplierEntity result = supplierService.addSupplier(reqBody);
            return new ResponseEntity<>(new GenericResponse(result, "Add Suppliers Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> updateSupplier(
            @PathVariable String id,
            @RequestBody SupplierRequestDto reqBody
    ) {
        try {
            SupplierEntity result = supplierService.updateSupplier(id, reqBody);
            if(result!=null) {
                return new ResponseEntity<>(new GenericResponse(result, "Update Suppliers Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new GenericResponse(null, "Suppler ID Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteSupplier(
            @PathVariable String id
    ) {
        try {
            SupplierEntity result = supplierService.deleteSupplier(id);
            if(result!=null) {
                return new ResponseEntity<>(new GenericResponse(result, "Delete Suppliers Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new GenericResponse(null, "Suppler ID Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
