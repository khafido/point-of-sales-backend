package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> add(@RequestBody CategoryDto req){
        var category = categoryService.add(req);
        return new ResponseEntity<GenericResponse>
                (new GenericResponse(category, "Category Created"), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll(){
        var categories = categoryService.getAll();
        return new ResponseEntity<GenericResponse>
                (new GenericResponse(categories, "OK"), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<GenericResponse> getActive(){
        var activeCats = categoryService.getActiveCategory();
        return new ResponseEntity<GenericResponse>
                (new GenericResponse(activeCats, "OK"), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("id") UUID id){
        var category  = categoryService.getById(id);
        return new ResponseEntity<GenericResponse>
                (new GenericResponse(category, "OK"), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") UUID id,
                                                  @RequestBody CategoryDto req){
        var category = categoryService.update(id, req);
        return new ResponseEntity<GenericResponse>
                (new GenericResponse(category, "Category updated"), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable("id") UUID id){
        var category = categoryService.delete(id);
        return new ResponseEntity<GenericResponse>
                (new GenericResponse(category, "Category deleted"), HttpStatus.OK);
    }

}
