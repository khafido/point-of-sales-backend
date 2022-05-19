package com.mitrais.cdcpos.dto.store;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StoreAddItemRequestDto {

    @NotEmpty
    private String itemId;
}
