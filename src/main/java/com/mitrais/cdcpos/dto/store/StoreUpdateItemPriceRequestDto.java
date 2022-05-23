package com.mitrais.cdcpos.dto.store;

import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StoreUpdateItemPriceRequestDto {

    @NotEmpty
    public String priceMode;

    @NotNull
    public BigDecimal fixedPrice;
}
