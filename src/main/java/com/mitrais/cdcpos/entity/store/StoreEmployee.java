package com.mitrais.cdcpos.entity.store;


import com.mitrais.cdcpos.entity.auth.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_employee")
public class StoreEmployee {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @ManyToOne()
    @Column(name = "user_id")
    private UserEntity user;

    @ManyToOne()
    @Column(name = "store_id")
    private StoreEntity store;
}
