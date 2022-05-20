package com.mitrais.cdcpos.entity.store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mitrais.cdcpos.entity.EntityAudit;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreEntity extends EntityAudit implements Serializable {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "store")
    private List<StoreEmployee> employees;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private UserEntity manager;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<StoreItemEntity> storeItems;



}
