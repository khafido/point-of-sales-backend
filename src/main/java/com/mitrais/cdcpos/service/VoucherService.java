package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.CategoryDto;
import com.mitrais.cdcpos.dto.VoucherDto;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.VoucherEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherEntity add(VoucherDto req){
        VoucherEntity voucherEntity = new VoucherEntity();
        voucherEntity.setName(req.getName());
        voucherEntity.setCode(req.getCode());
        voucherEntity.setValue(req.getValue());
        voucherEntity.setStartDate(req.getStartDate());
        voucherEntity.setEndDate(req.getEndDate());
        voucherEntity.setMinimumPurchase(req.getMinimumPurchase());
        voucherEntity.setDescription(req.getDescription());
        return voucherRepository.save(voucherEntity);
    }

    public Page<VoucherEntity> getAll(boolean paginated, int page, int size,
                                       String searchVal, String sortBy, String sortDirection){
        Sort sort;
        Pageable paging;
        Page<VoucherEntity> result;

        if("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if(paginated){
            paging = PageRequest.of(page, size,sort);
            result = voucherRepository.findAllSearch(paging, searchVal);
        }else{
            List<VoucherEntity> list = voucherRepository.findAllSearch(sort, searchVal);
            result = new PageImpl<>(list);
        }
        return result;
    }

    public VoucherEntity update(UUID id, VoucherDto req){
        var voucherEntity = voucherRepository.getById(id);
        voucherEntity.setName(req.getName());
        voucherEntity.setValue(req.getValue());
        voucherEntity.setStartDate(req.getStartDate());
        voucherEntity.setEndDate(req.getEndDate());
        voucherEntity.setMinimumPurchase(req.getMinimumPurchase());
        voucherEntity.setDescription(req.getDescription());
        return voucherRepository.save(voucherEntity);
    }

    public VoucherEntity delete(UUID id){
        var voucherEntity = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("voucher","id",id));
        voucherEntity.setDeletedAt(LocalDateTime.now());
        return voucherRepository.save(voucherEntity);
    }

    public boolean isVoucherExist(String code){
        return voucherRepository.existsByCode(code);
    }
}
