package com.mitrais.cdcpos.controller;


import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.StoreDto;
import com.mitrais.cdcpos.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping()
    public ResponseEntity<GenericResponse> fetchAll() {
        var content = storeService.fetchAll();
        return new ResponseEntity<>(new GenericResponse(content, "Successfully get data"), HttpStatus.OK);
    }

    @GetMapping("/{pageSize}/{pageOffset}")
    public ResponseEntity<GenericResponse> fetchWithPagination(@PathVariable int pageOffset, @PathVariable int pageSize) {
        var paginatedContent = storeService.fetchAllWithPagination(pageOffset, pageSize);
        return new ResponseEntity<>(new GenericResponse(paginatedContent, "Successfully get data"), HttpStatus.OK);
    }

    @GetMapping("id/{uuid}")
    public ResponseEntity<GenericResponse> fetchById(@PathVariable UUID id){
        var store = storeService.fetchById(id);
        if(store.isPresent()){
            return new ResponseEntity<>(new GenericResponse(store.get(), "Successfully get Data"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new GenericResponse(null, "Data not exist"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<GenericResponse> create(@RequestBody StoreDto req) {
        try {
            var newStore = storeService.create(req);
            return new GenericResponse(newStore, "New Store Created");
        } catch (Exception e) {
            return new GenericResponse(null, "Failed to Create New Store");
        }
    }

}
