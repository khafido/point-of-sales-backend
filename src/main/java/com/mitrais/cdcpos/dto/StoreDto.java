package com.mitrais.cdcpos.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StoreDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String location;
}
