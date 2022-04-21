package com.mitrais.cdcpos.entity.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRoleEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    public UserRoleEntity(Long userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
