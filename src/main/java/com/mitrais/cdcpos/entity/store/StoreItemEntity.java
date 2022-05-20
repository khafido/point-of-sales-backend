package com.mitrais.cdcpos.entity.store;


import com.mitrais.cdcpos.entity.EntityAudit;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_item")
public class StoreItemEntity extends EntityAudit {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @ManyToOne()
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne()
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private ItemEntity item;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "fixed_price")
    private BigDecimal fixedPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_mode")
    private PriceMode priceMode;

    public enum PriceMode {
        FIXED,
        BY_SYSTEM
    }
}
