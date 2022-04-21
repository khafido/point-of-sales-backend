package com.mitrais.cdcpos.entity.auth;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {

    private Long userId;

    private Integer roleId;

    public UserRoleId(Long userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRoleId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
