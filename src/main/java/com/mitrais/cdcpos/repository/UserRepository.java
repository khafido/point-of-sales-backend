package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);

    @Query("select u from UserEntity u where u.deletedAt is null")
    List<UserEntity> findByDeletedAtIsNull();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<UserEntity> findByRoles_Name(ERole name);

    @Query("select u.username from UserEntity u")
    List<String> findAllUsername();

    @Query("select u.email from UserEntity u")
    List<String> findAllEmail();


}
