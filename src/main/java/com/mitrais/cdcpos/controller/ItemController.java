package com.mitrais.cdcpos.controller;


import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.ItemRequestDto;
import com.mitrais.cdcpos.dto.ItemResponseDto;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin(origins="*", maxAge=3600)
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> add(@RequestBody ItemRequestDto req) {
        try {
            ItemEntity result = itemService.add(req);
            return new ResponseEntity<>(new GenericResponse(result, "Add Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAll(@RequestParam(defaultValue = "false") boolean isPaginated,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "") String searchValue,
                                                  @RequestParam(defaultValue = "name") String sortBy,
                                                  @RequestParam(defaultValue = "ASC") String sortDirection)
    {
        try {
            PaginatedDto<ItemResponseDto> result = itemService.getAll(isPaginated, page, size, searchValue, sortBy, sortDirection);
            return new ResponseEntity<>(new GenericResponse(result, "Get All Items Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("id") UUID id) {
        try {
            ItemEntity result = itemService.getById(id);
            return new ResponseEntity<>(new GenericResponse(result, "Get Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") UUID id, @RequestBody ItemRequestDto req){
        try {
            ItemEntity result = itemService.update(id, req);
            return new ResponseEntity<>(new GenericResponse(result, "Update Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable("id") UUID id) {
        try {
            ItemEntity result = itemService.delete(id);
            return new ResponseEntity<>(new GenericResponse(result, "Delete Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("check-barcode")
    public boolean checkBarcode(@RequestParam(defaultValue = "") String barcode) {
        return itemService.isBarcodeExist(barcode);
    }
}
