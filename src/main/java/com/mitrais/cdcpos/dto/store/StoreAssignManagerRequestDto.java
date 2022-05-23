package com.mitrais.cdcpos.dto.store;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StoreAssignManagerRequestDto {

    @NotEmpty
    private String userId;
}
