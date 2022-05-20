package com.mitrais.cdcpos.entity.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mitrais.cdcpos.entity.EntityAudit;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incoming_item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IncomingItemEntity extends EntityAudit implements Serializable {
    @Id
    @Column(name="id")
    private UUID transferId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private StoreItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private SupplierEntity supplier;

    @Column(name = "buy_qty")
    private Long buy_qty;

    @Column(name = "buy_date")
    private LocalDateTime buy_date;

    @Column(name = "expiry_date")
    private LocalDate expiry_date;
}
