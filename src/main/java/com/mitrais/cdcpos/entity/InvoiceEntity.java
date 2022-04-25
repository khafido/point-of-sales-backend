package com.mitrais.cdcpos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice")
public class InvoiceEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "payment_option")
    private String paymentOption;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "change_amount")
    private BigDecimal changeAmount;

    @ManyToOne(fetch=FetchType.LAZY)
    private UUID voucherId;

    @OneToOne(fetch = FetchType.LAZY)
    private UUID cashierId;

    @ManyToOne(fetch = FetchType.LAZY)
    private UUID storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private UUID customerId;
}
