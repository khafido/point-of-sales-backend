package com.mitrais.cdcpos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voucher")
public class VoucherEntity {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "minimum_purchase")
    private BigDecimal minimumPurchase;

    @Column(name = "description")
    private String description;


}
