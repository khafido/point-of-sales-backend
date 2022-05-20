package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomingItemDto {
    UUID itemId;
    UUID supplierId;
    long qty;
    LocalDateTime buyDate;
    LocalDate expiryDate;
}
