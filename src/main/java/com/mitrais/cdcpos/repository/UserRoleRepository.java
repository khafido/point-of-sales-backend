package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.auth.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
    UserRoleEntity findByRoleId(Integer roleId);
}
