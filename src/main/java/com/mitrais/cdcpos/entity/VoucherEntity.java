package com.mitrais.cdcpos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voucher", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class VoucherEntity extends EntityAudit {

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
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "minimum_purchase")
    private BigDecimal minimumPurchase;

    @Column(name = "description")
    private String description;


}
