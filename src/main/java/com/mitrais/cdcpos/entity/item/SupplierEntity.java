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
@Table(name = "suppliers")
public class SupplierEntity {
    @Id
    private UUID transferId = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "cp_name")
    private String CPName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;
}
