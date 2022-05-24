package com.mitrais.cdcpos.dto.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.dto.ItemResponseDto;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreListOfItemsResponseDto extends ItemResponseDto {

    private UUID storeItemId;
    private Integer stock;
    private BigDecimal fixedPrice;
    private BigDecimal bySystemPrice;
    private String priceMode;

    public StoreListOfItemsResponseDto(UUID id, String name, String image, String barcode, String category, String packaging, LocalDateTime deletedAt, UUID storeItemId,Integer stock, BigDecimal fixedPrice, BigDecimal bySystemPrice, String priceMode) {
        super(id, name, image, barcode, category, packaging, deletedAt);
        this.storeItemId = storeItemId;
        this.stock = stock;
        this.fixedPrice = fixedPrice;
        this.bySystemPrice = bySystemPrice;
        this.priceMode = priceMode;
    }

    public static StoreListOfItemsResponseDto toDto (StoreItemEntity entity, BigDecimal bySystemPrice) {
        return new StoreListOfItemsResponseDto(
                entity.getItem().getId(),
                entity.getItem().getName(),
                entity.getItem().getImage(),
                entity.getItem().getBarcode(),
                entity.getItem().getCategory().getName(),
                entity.getItem().getPackaging(),
                null, //deletedAt
                entity.getId(),
                entity.getStock(),
                entity.getFixedPrice(),
                bySystemPrice,
                entity.getPriceMode().toString()
        );
    }
}
