package com.mitrais.cdcpos.controller;


import com.mitrais.cdcpos.dto.*;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.service.IncomingItemService;
import com.mitrais.cdcpos.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin(origins="*", maxAge=3600)
@RequiredArgsConstructor
public class ItemController {
    @Autowired
    ItemService itemService;
    @Autowired
    IncomingItemService incomingItemService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> add(@RequestBody ItemRequestDto req) {
        try {
            ItemEntity result = itemService.add(req);
            ItemResponseDto resultDto = ItemResponseDto.toDto(result);
            return new ResponseEntity<>(new GenericResponse(resultDto, "Add Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
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
                                                  @RequestParam(defaultValue = "ASC") String sortDirection,
                                                  @RequestParam(defaultValue = "true") boolean fullInformation)
    {
        try {
            PaginatedDto<ItemResponseDto> result = itemService.getAll(isPaginated, page, size, searchValue, sortBy, sortDirection, fullInformation);
            return new ResponseEntity<>(new GenericResponse(result, "Get All Items Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("id") UUID id) {
        try {
            ItemEntity result = itemService.getById(id);
            ItemResponseDto resultDto = ItemResponseDto.toDto(result);
            return new ResponseEntity<>(new GenericResponse(resultDto, "Get Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") UUID id, @RequestBody ItemRequestDto req){
        try {
            ItemEntity result = itemService.update(id, req);
            ItemResponseDto resultDto = ItemResponseDto.toDto(result);
            return new ResponseEntity<>(new GenericResponse(resultDto, "Update Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable("id") UUID id) {
        try {
            ItemEntity result = itemService.delete(id);
            ItemResponseDto resultDto = ItemResponseDto.toDto(result);
            return new ResponseEntity<>(new GenericResponse(resultDto, "Delete Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("check-barcode-add")
    public boolean checkBarcodeOnAdd(@RequestParam(defaultValue = "") String barcode) {
        return itemService.checkBarcodeOnAdd(barcode);
    }

    @GetMapping("check-barcode-update/{id}")
    public boolean checkBarcodeOnEdit(@PathVariable("id") UUID id, @RequestParam(defaultValue = "") String barcode) {
        return itemService.checkBarcodeOnUpdate(id, barcode);
    }

    /*
    * Add incoming item
    * */
    @PostMapping("stock")
    public ResponseEntity<GenericResponse> addIncomingItem(@RequestBody IncomingItemDto req){
        try{
            var incomingItem = incomingItemService.add(req);
            if(incomingItem!=null){
                IncomingItemResponseDto res = IncomingItemResponseDto.toDto(incomingItem);
                return new ResponseEntity<>
                        (new GenericResponse(res, "Add incoming item success", GenericResponse.Status.CREATED), HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(new GenericResponse(null, "Store/Item Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("stock")
    public ResponseEntity<GenericResponse> getAllIncomingItem(@RequestParam(defaultValue = "false") boolean isPaginated,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "") String search,
                                                              @RequestParam(defaultValue = "storeItem.item.name") String sortBy,
                                                              @RequestParam(defaultValue = "ASC") String sortDirection,
                                                              @RequestParam(required = false) LocalDateTime start,
                                                              @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime end){

        try{
            Page<IncomingItemResponseDto> incomingItem = incomingItemService.getAll(isPaginated,page, size,search,sortBy,sortDirection,start,end);
            PaginatedDto<IncomingItemResponseDto> result = new PaginatedDto<>(
                    incomingItem.getContent(),
                    incomingItem.getNumber(),
                    incomingItem.getTotalPages()
            );
            return new ResponseEntity<>
                    (new GenericResponse(result, "Get incoming item success", GenericResponse.Status.SUCCESS),
                            HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>
                    (new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
