package com.mitrais.cdcpos.entity.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.EntityAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class ItemEntity extends EntityAudit {
    @Id
    @Column(name="id")
    private UUID transferId = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "barcode")
    private String barcode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "packaging")
    private String packaging;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IncomingItemEntity> incomingItems = new ArrayList<>();
}
