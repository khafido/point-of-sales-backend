package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.dto.store.StoreAddItemRequestDto;
import com.mitrais.cdcpos.dto.store.StoreAssignManagerRequestDto;
import com.mitrais.cdcpos.dto.store.StoreDto;
import com.mitrais.cdcpos.dto.store.StoreUpdateItemPriceRequestDto;
import com.mitrais.cdcpos.exception.ManualValidationFailException;
import com.mitrais.cdcpos.service.StoreService;
import lombok.RequiredArgsConstructor;
import com.mitrais.cdcpos.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/v1/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping()
    public ResponseEntity<GenericResponse> getAll(@RequestParam(defaultValue = "false") Boolean isPaginated, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "") String searchValue, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "DESC") String sortDirection) {
        try {
            var result = storeService.getAll(isPaginated, page, size, searchValue, sortBy, sortDirection);
            var resultDto = result.getContent().stream().map(StoreDto::toDto).collect(Collectors.toList());
            var paginatedDto = new PaginatedDto<>(resultDto, result.getNumber(), result.getTotalPages());
            var genericResponse = new GenericResponse(paginatedDto, "Successfully Get Store Data", GenericResponse.Status.SUCCESS);
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, "Failed To Get Store Data", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable UUID id) {
        try {
            var store = storeService.getById(id);
            if (store.isPresent()) {
                var genericResponse = new GenericResponse(StoreDto.toDto(store.get()), "Successfully get Store Data", GenericResponse.Status.SUCCESS);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            } else {
                var genericResponse = new GenericResponse(null, "Store Data doesn't exist", GenericResponse.Status.ERROR_NOT_FOUND);
                return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, "Failed To Get Store Data", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<GenericResponse> create(@RequestBody StoreDto req) {
        try {
            var newStore = storeService.create(req);
            var genericResponse = new GenericResponse(newStore, "New Store Created", GenericResponse.Status.SUCCESS);
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, "Failed to Create New Store", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable UUID id, @RequestBody StoreDto storeDto) {
        try {
            var store = storeService.update(id, storeDto);

            if (store != null) {
                var resultDto = StoreDto.toDto(store);
                var genericResponse = new GenericResponse(resultDto, "Successfully Update Store Data", GenericResponse.Status.SUCCESS);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            } else {
                var genericResponse = new GenericResponse(null, "Store Data doesn't exist", GenericResponse.Status.ERROR_NOT_FOUND);
                return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, "Failed To Update Store Data", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID id) {
        try {
            var store = storeService.delete(id);
            if (store != null) {
                var resultDto = StoreDto.toDto(store);
                var genericResponse = new GenericResponse(resultDto, "Successfully Delete Store Data", GenericResponse.Status.SUCCESS);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            } else {
                var genericResponse = new GenericResponse(null, "Store Data doesn't exist", GenericResponse.Status.ERROR_NOT_FOUND);
                return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, "Failed To Delete Store Data", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/item")
    public ResponseEntity<GenericResponse> addItemToStore(@PathVariable UUID id, @RequestBody @Valid StoreAddItemRequestDto request) {
        try {
            var result = storeService.addItemToStore(id ,request);
            if(result!=null) {
                return new ResponseEntity<>(new GenericResponse(result, "Add Item to Store Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new GenericResponse(null, "Store/Item Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/item")
    public ResponseEntity<GenericResponse> storeListOfItems(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "false") Boolean isPaginated,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        try {
            var result = storeService.storeListOfItems(id, isPaginated, page, size, searchValue, sortBy, sortDirection);
            var paginatedDto = new PaginatedDto<>(result.getContent(), result.getNumber(), result.getTotalPages());
            return new ResponseEntity<>(new GenericResponse(paginatedDto, "Get Store List of Items Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/item/{itemId}")
    public ResponseEntity<GenericResponse> updateStoreItemPrice(
            @PathVariable UUID id,
            @PathVariable UUID itemId,
            @RequestBody @Valid StoreUpdateItemPriceRequestDto request) {
        try {
            var result = storeService.updateStoreItemPrice(id, itemId, request);
            if(result!=null) {
                return new ResponseEntity<>(new GenericResponse(result, "Update Store Item Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new GenericResponse(null, "Store Item Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        } catch (ManualValidationFailException e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INPUT);
            return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/manager")
    public ResponseEntity<GenericResponse> assignManager(@PathVariable UUID id, @RequestBody @Valid StoreAssignManagerRequestDto request) {
        try {
            var result = storeService.assignManager(id, request);
            if (result != null) {
                var resultDto = StoreDto.toDto(result);
                return new ResponseEntity<>(new GenericResponse(resultDto, "Assign Store Manager Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new GenericResponse(null, "Store/User Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        } catch (ManualValidationFailException e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INPUT);
            return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/employee")
    public ResponseEntity<GenericResponse> getStoreEmployee(@PathVariable UUID id, @RequestParam(defaultValue = "false") Boolean isPaginated, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "") String searchValue, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "DESC") String sortDirection) {
        try {
            var store = storeService.getById(id);
            if (store.isPresent()) {
                var result = storeService.getStoreEmployee(id, isPaginated, page, size, searchValue, sortBy, sortDirection);
                var resultDto = result.getContent().stream().map(StoreEmployeeDto::toDtoWithUser).collect(Collectors.toList());
                var paginatedDto = new PaginatedDto<>(resultDto, result.getNumber(), result.getTotalPages());
                var genericResponse = new GenericResponse(paginatedDto, "Successfully get Store Employee Data", GenericResponse.Status.SUCCESS);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            } else {
                var genericResponse = new GenericResponse(null, "Store doesn't exist", GenericResponse.Status.ERROR_NOT_FOUND);
                return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            var genericResponse = new GenericResponse(null, "Failed To Get Store Employee Data", GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-employee")
    public ResponseEntity<GenericResponse> addEmployee(@RequestBody @Valid AddEmployeeDto request) {
        try {
            var result = storeService.addEmployee(request);
            if (result != null) {
                var resultDto = StoreEmployeeDto.toDto(result);
                return new ResponseEntity<>(new GenericResponse(resultDto, "Add Store Employee Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new GenericResponse(null, "Store/User Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        } catch (ManualValidationFailException e) {
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INPUT);
            return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var genericResponse = new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
