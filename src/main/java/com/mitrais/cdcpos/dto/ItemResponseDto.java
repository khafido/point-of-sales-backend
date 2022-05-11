package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private UUID id;

    private String name;

    private String image;

    private String barcode;

    private String category;

    private String packaging;

    private LocalDateTime deletedAt;
}
