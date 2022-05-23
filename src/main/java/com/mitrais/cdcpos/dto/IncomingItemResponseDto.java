package com.mitrais.cdcpos.dto;

import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomingItemResponseDto {
    private UUID id;
    private String itemName;
    private String supplierName;
    private long qty;
    private BigDecimal price;
    private LocalDateTime buyDate;
    private LocalDate expiryDate;

    public static IncomingItemResponseDto toDto(IncomingItemEntity entity){
        return new IncomingItemResponseDto(
                entity.getId(),
                entity.getStoreItem().getItem().getName(),
                entity.getSupplier().getName(),
                entity.getBuyQty(),
                entity.getBuyPrice(),
                entity.getBuyDate(),
                entity.getExpiryDate()
        );
    }

}
