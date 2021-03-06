package com.mitrais.cdcpos.entity.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.EntityAudit;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "item", uniqueConstraints = @UniqueConstraint(columnNames = "barcode"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ItemEntity extends EntityAudit implements Serializable {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "image", columnDefinition="TEXT")
    private String image;

    @Column(name = "barcode")
    private String barcode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;

    @Column(name = "packaging")
    private String packaging;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<StoreItemEntity> storeItems;
}
