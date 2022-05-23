package com.mitrais.cdcpos.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddEmployeeDto {

    @NotEmpty
    private String storeId;

    @NotEmpty
    private String userId;

}
