package com.mitrais.cdcpos.entity.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRoleEntity {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    public UserRoleEntity(UUID userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
