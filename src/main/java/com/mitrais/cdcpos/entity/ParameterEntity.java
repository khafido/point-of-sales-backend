package com.mitrais.cdcpos.entity;

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

@Table(name = "parameter")
public class ParameterEntity {
    @Id
    @Column(name = "id")
    private UUID id =  UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
