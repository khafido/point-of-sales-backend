package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("SELECT u FROM UserEntity u WHERE u.deletedAt IS NULL AND (" +
            "u.username LIKE %:search% OR " +
            "u.firstName LIKE %:search% OR " +
            "u.lastName LIKE %:search% OR " +
            "u.phone LIKE %:search% OR " +
            "u.email LIKE %:search% OR " +
            "u.address LIKE %:search%)")
    Page<UserEntity> findAllSearch(Pageable pageable, @Param("search") String searchVal);

    @Query("SELECT u FROM UserEntity u WHERE u.deletedAt IS NULL AND (" +
            "u.username LIKE %:search% OR " +
            "u.firstName LIKE %:search% OR " +
            "u.lastName LIKE %:search% OR " +
            "u.phone LIKE %:search% OR " +
            "u.email LIKE %:search% OR " +
            "u.address LIKE %:search%)")
    List<UserEntity> findAllSearch(Sort sort, @Param("search") String searchVal);

    UserEntity findByUsername(String username);

    @Query("select u from UserEntity u where u.deletedAt is null ORDER BY u.firstName, u.lastName ASC")
    List<UserEntity> findByDeletedAtIsNull();

    @Query("select u from UserEntity u where u.id = ?1 and u.deletedAt is null")
    UserEntity findByIdAndDeletedAtIsNull(UUID id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<UserEntity> findByRoles_Name(ERole name);

    @Query("select u.username from UserEntity u")
    List<String> findAllUsername();

    @Query("select u.email from UserEntity u")
    List<String> findAllEmail();


}
