package com.mitrais.cdcpos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parameter", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ParameterEntity extends EntityAudit {
    @Id
    @Column(name = "id")
    private UUID id =  UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private int value;
}
