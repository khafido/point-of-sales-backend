package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.IncomingItemDto;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.IncomingItemRepository;
import com.mitrais.cdcpos.repository.StoreItemRepository;
import com.mitrais.cdcpos.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        incomingItem.setBuyDate(req.getBuyDate());
        incomingItem.setExpiryDate(req.getExpiryDate());

        return incomingItemRepository.save(incomingItem);
    }

    public List<IncomingItemEntity> getAll(){
        return incomingItemRepository.findAll();
    }


}
