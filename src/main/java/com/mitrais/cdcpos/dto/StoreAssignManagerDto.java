package com.mitrais.cdcpos.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class StoreAssignManagerDto {

    @NotEmpty
    private String storeId;

    @NotEmpty
    private String userId;
}
