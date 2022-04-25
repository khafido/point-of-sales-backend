package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<UserEntity> findByRoles_Name(ERole name);
}
