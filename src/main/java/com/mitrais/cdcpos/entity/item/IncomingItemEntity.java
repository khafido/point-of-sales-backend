package com.mitrais.cdcpos.entity.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incoming_item")
public class IncomingItemEntity {
    @Id
    private UUID transferId = UUID.randomUUID();

    @Column(name = "item_id")
    private String item_id;

    @Column(name = "supplier_id")
    private String supplier_id;

    @Column(name = "buy_qty")
    private Long buy_qty;

    @Column(name = "buy_date")
    private LocalDateTime buy_date;

    @Column(name = "expiry_date")
    private LocalDate expiry_date;
}
