package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/category")
@CrossOrigin(origins="*", maxAge=3600)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> add(@RequestBody @Valid CategoryDto req){
        CategoryEntity category;
        // handling unique
        try{
            category = categoryService.add(req);
            return new ResponseEntity<>
                    (new GenericResponse(category, "Category Created", GenericResponse.Status.CREATED), HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Category already exist", GenericResponse.Status.ERROR_INPUT),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll(@RequestParam(defaultValue = "false") boolean isPaginated,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "") String searchVal,
                                                  @RequestParam(defaultValue = "name") String sortBy,
                                                  @RequestParam(defaultValue = "ASC") String sortDirection){
        try{
            Page<CategoryEntity> categories = categoryService.getAll(isPaginated,page,size,searchVal,sortBy,sortDirection);
            PaginatedDto<CategoryEntity> result = new PaginatedDto<>(
                    categories.getContent(),
                    categories.getNumber(),
                    categories.getTotalPages()
            );
            return new ResponseEntity<>(new GenericResponse(result, "Get category list success", GenericResponse.Status.SUCCESS)
            ,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>
                    (new GenericResponse(null,e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable("id") UUID id){
        var category  = categoryService.getById(id);
        return new ResponseEntity<>
                (new GenericResponse(category, "OK"), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") UUID id,
                                                  @RequestBody @Valid CategoryDto req){
        CategoryEntity category;
        // handling unique
        try{
           category = categoryService.update(id, req);
            return new ResponseEntity<>
                    (new GenericResponse(category, "Category updated", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Category already exist", GenericResponse.Status.ERROR_INPUT),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable("id") UUID id){
        var category = categoryService.delete(id);
        return new ResponseEntity<>
                (new GenericResponse(category, "Category deleted"), HttpStatus.OK);
    }

}
