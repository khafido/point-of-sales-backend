package com.mitrais.cdcpos.dto.store;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class StoreAddItemRequestDto {

    @NotEmpty
    private List<String> itemIdList;
}
