package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponseDto {
    private UUID id;

    private String name;

    private String image;

    private String barcode;

    private String category;

    private String packaging;

    private LocalDateTime deletedAt;

    public ItemResponseDto(UUID id, String name, String category, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.deletedAt = deletedAt;
    }

    public static ItemResponseDto toDto (ItemEntity entity) {
        return new ItemResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getImage(),
                entity.getBarcode(),
                entity.getCategory().getName(),
                entity.getPackaging(),
                entity.getDeletedAt()
        );
    }

    public static ItemResponseDto toDtoLite (ItemEntity entity) {
        return new ItemResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getCategory().getName(),
                entity.getDeletedAt()
        );
    }
}
