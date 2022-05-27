package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDto {
    String name;
    String code;
    BigDecimal value;
    Date start;
    Date end;
    BigDecimal minimumPurchase;
    String description;
}
