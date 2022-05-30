package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.dto.VoucherDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.VoucherEntity;
import com.mitrais.cdcpos.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/voucher")
@CrossOrigin(origins="*", maxAge=3600)
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> add(@RequestBody @Valid VoucherDto req){
        VoucherEntity voucher;
        try{
            voucher = voucherService.add(req);
            return new ResponseEntity<>
                    (new GenericResponse(voucher,"Voucher created", GenericResponse.Status.CREATED), HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Voucher with this code already exist", GenericResponse.Status.ERROR_INPUT), HttpStatus.BAD_REQUEST);
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
            Page<VoucherEntity> vouchers = voucherService.getAll(isPaginated,page,size,searchVal,sortBy,sortDirection);
            PaginatedDto<VoucherEntity> result = new PaginatedDto<>(
                    vouchers.getContent(),
                    vouchers.getNumber(),
                    vouchers.getTotalPages()
            );
            return new ResponseEntity<>(new GenericResponse(result, "Get voucher list success", GenericResponse.Status.SUCCESS)
                    ,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>
                    (new GenericResponse(null,e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") UUID id,
                                                  @RequestBody @Valid VoucherDto req){
        VoucherEntity voucher;
        // handling unique
        try{
            voucher = voucherService.update(id, req);
            return new ResponseEntity<>
                    (new GenericResponse(voucher, "Voucher updated", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Voucher already exist", GenericResponse.Status.ERROR_INPUT),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable("id") UUID id){
        var category = voucherService.delete(id);
        return new ResponseEntity<>
                (new GenericResponse(category, "Voucher deleted", GenericResponse.Status.SUCCESS), HttpStatus.OK);
    }

    @GetMapping("check-voucher/{voucher}")
    public boolean checkVoucher(@PathVariable("voucher") String voucher){
        return voucherService.isVoucherExist(voucher);
    }
}
