package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.IncomingItemDto;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.IncomingItemRepository;
import com.mitrais.cdcpos.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class IncomingItemService {
    private final IncomingItemRepository incomingItemRepository;
    private final SupplierRepository supplierRepository;
    // store item entity repo

    public IncomingItemEntity add(IncomingItemDto req){
        var incomingItem = new IncomingItemEntity();
        var supplier= supplierRepository.findByIdAndDeletedAtIsNull(req.getSupplierId())
                .orElseThrow(()-> new ResourceNotFoundException("Supplier", "id", req.getSupplierId()));
        incomingItem.setSupplier(supplier);
        incomingItem.setBuy_qty(req.getQty());
        incomingItem.setBuy_date(req.getBuyDate());
        incomingItem.setExpiry_date(req.getExpiryDate());
        return incomingItemRepository.save(incomingItem);
    }


}
