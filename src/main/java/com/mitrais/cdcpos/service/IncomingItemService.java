package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.IncomingItemDto;
import com.mitrais.cdcpos.dto.IncomingItemResponseDto;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.IncomingItemRepository;
import com.mitrais.cdcpos.repository.StoreItemRepository;
import com.mitrais.cdcpos.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.math.BigDecimal;
import java.math.MathContext;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IncomingItemService {
    private final IncomingItemRepository incomingItemRepository;
    private final SupplierRepository supplierRepository;
    private final StoreItemRepository storeItemRepository;


    public IncomingItemEntity add(IncomingItemDto req){
        var incomingItem = new IncomingItemEntity();
        var storeItem = storeItemRepository.findById(req.getStoreItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Store Item", "id", req.getStoreItemId()));
        
        var supplier= supplierRepository.findByIdAndDeletedAtIsNull(req.getSupplierId())
                .orElseThrow(()-> new ResourceNotFoundException("Supplier", "id", req.getSupplierId()));

        storeItem.setStock((int) (storeItem.getStock()+req.getQty()));
        storeItemRepository.save(storeItem);

        incomingItem.setStoreItem(storeItem);
        incomingItem.setSupplier(supplier);
        incomingItem.setBuyQty(req.getQty());
        incomingItem.setBuyPrice(req.getBuyPrice());
        MathContext mc = new MathContext(String.valueOf(req.getBuyPrice()).length()-1);
        incomingItem.setPricePerItem(req.getBuyPrice().divide(BigDecimal.valueOf(req.getQty()), mc));
        incomingItem.setBuyDate(req.getBuyDate());
        incomingItem.setExpiryDate(req.getExpiryDate());

        return incomingItemRepository.save(incomingItem);
    }

    public Page<IncomingItemResponseDto> getAll(boolean paginated, int page, int size,
                                                String search, String sortBy, String sortDirection,
                                                LocalDateTime start, LocalDateTime end){
        Sort sort;
        Pageable paging;
        Page<IncomingItemEntity> incomingItemEntities;

        if(sortBy.equalsIgnoreCase("supplier")){
            sortBy = sortBy.concat(".name");
        }else{
            sortBy = "storeItem.item.name";
        }

        if("DESC".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        if(paginated){
            paging = PageRequest.of(page, size,sort);
            incomingItemEntities = incomingItemRepository.findAllSearch(paging, search, start,end);
            var result = incomingItemEntities.map(IncomingItemResponseDto::toDto);
            return result;
        }else{
            var list = incomingItemRepository.findAllSearch(sort,search,start,end);
            var result = list.stream().map(IncomingItemResponseDto::toDto).collect(Collectors.toList());
            return new PageImpl<>(result);
        }

    }


}
