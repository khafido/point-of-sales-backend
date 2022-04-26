package com.mitrais.cdcpos.entity.store;

import com.mitrais.cdcpos.entity.EntityAudit;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import lombok.*;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")
public class StoreEntity extends EntityAudit {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToOne()
    //@Column(name = "manager_id")
    private UserEntity manager;
}
