package com.mitrais.cdcpos.entity.store;


import com.mitrais.cdcpos.entity.EntityAudit;
import com.mitrais.cdcpos.entity.auth.UserEntity;
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
@Table(name = "store_employee")
public class StoreEmployeeEntity extends EntityAudit {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne()
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;
}
