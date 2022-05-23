package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomingItemDto {
    @NotEmpty
    UUID storeItemId;
    @NotEmpty
    UUID supplierId;
    @NotEmpty
    BigDecimal buyPrice;
    @NotEmpty
    long qty;
    @NotEmpty
    LocalDateTime buyDate;
    @NotEmpty
    LocalDate expiryDate;
}
