package com.mitrais.cdcpos.dto;

import com.mitrais.cdcpos.entity.CategoryEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Image cannot be empty")
    private String image;

    private String barcode;

    @NotEmpty(message = "Category cannot be empty")
    private String category;

    private String packaging;
}
