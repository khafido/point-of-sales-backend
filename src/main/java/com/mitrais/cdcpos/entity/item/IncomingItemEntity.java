package com.mitrais.cdcpos.entity.item;

import com.mitrais.cdcpos.entity.EntityAudit;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incoming_item")
public class IncomingItemEntity extends EntityAudit {
    @Id
    @Column(name="id")
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_item_id", referencedColumnName = "id")
    private StoreItemEntity storeItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private SupplierEntity supplier;

    @Column(name = "buy_qty")
    private Long buyQty;

    @Column(name = "buy_price")
    private BigDecimal buyPrice;

    @Column(name = "buy_date")
    private LocalDateTime buyDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;
}
