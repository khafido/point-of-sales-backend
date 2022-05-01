package com.mitrais.cdcpos.controller;


import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "api/v1/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping()
    public ResponseEntity<GenericResponse> getAll(
            @RequestParam(defaultValue = "false") Boolean isPaginated,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        try {
            var result = storeService.getAll(isPaginated, page, size, searchValue, sortBy, sortDirection);
            var paginatedDto = new PaginatedDto<>(result.getContent(), result.getNumber(), result.getTotalPages());
            var genericResponse = new GenericResponse(paginatedDto, "Successfully Get Store Data", GenericResponse.Status.SUCCESS);
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        } catch (Exception e) {
            var genericResponse = new GenericResponse(null, "Failed To Get Store Data", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id/{uuid}")
    public ResponseEntity<GenericResponse> getById(@PathVariable UUID id){
        var store = storeService.getById(id);
        try{
            if(store.isPresent()){
                var genericResponse = new GenericResponse(store.get(), "Successfully get Store Data", GenericResponse.Status.SUCCESS);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            }else{
                var genericResponse = new GenericResponse(null, "Store Data doesn't exist", GenericResponse.Status.ERROR_NOT_FOUND);
                return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            var genericResponse = new GenericResponse(null, "Failed To Get Store Data", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//    @PostMapping()
//    public ResponseEntity<GenericResponse> create(@RequestBody StoreDto req) {
//        try {
//            var newStore = storeService.create(req);
//            return new GenericResponse(newStore, "New Store Created");
//        } catch (Exception e) {
//            return new GenericResponse(null, "Failed to Create New Store");
//        }
//    }

}
