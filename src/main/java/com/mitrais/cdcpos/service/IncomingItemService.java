package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.IncomingItemDto;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.IncomingItemRepository;
import com.mitrais.cdcpos.repository.StoreItemRepository;
import com.mitrais.cdcpos.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class IncomingItemService {
    private final IncomingItemRepository incomingItemRepository;
    private final SupplierRepository supplierRepository;
    private final StoreItemRepository storeItemRepository;

    public IncomingItemEntity add(IncomingItemDto req){
        var incomingItem = new IncomingItemEntity();
        var store = storeItemRepository.findById(req.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Store Item", "id", req.getItemId()));
        
        var supplier= supplierRepository.findByIdAndDeletedAtIsNull(req.getSupplierId())
                .orElseThrow(()-> new ResourceNotFoundException("Supplier", "id", req.getSupplierId()));
        
        incomingItem.setSupplier(supplier);
        incomingItem.setBuyPrice(req.getBuyPrice());
        incomingItem.setBuyQty(req.getQty());
        incomingItem.setBuyDate(req.getBuyDate());
        incomingItem.setExpiryDate(req.getExpiryDate());
        return incomingItemRepository.save(incomingItem);
    }


}
