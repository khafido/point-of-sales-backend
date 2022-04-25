package com.mitrais.cdcpos.entity.store;


import com.mitrais.cdcpos.entity.auth.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_item")
public class StoreItemEntity {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @ManyToOne()
    @Column(name = "store_id")
    private StoreEntity store;

    @ManyToOne()
    @Column(name = "item_id")
    private String item;

    @Column(name = "stock")
    private Integer stock;
}
