package com.mitrais.cdcpos.entity.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class ItemEntity {
    @Id
    private UUID transferId = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "category_id")
    private UUID category;

    @Column(name = "packaging")
    private String packaging;
}
