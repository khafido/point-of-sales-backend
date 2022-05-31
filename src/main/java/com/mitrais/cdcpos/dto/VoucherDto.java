package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDto {
    String name;
    String code;
    BigDecimal value;
    LocalDateTime start;
    LocalDateTime end;
    BigDecimal minimumPurchase;
    String description;
}
