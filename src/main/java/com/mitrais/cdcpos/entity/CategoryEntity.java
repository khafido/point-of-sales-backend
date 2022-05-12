package com.mitrais.cdcpos.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "category", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class CategoryEntity extends EntityAudit {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "name")
    private String name;
}
