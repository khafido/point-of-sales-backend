package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.SupplierEntity;
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
public interface SupplierRepository extends JpaRepository<SupplierEntity, UUID> {

    @Query("SELECT s FROM SupplierEntity s WHERE s.deletedAt IS NULL AND (" +
            "s.name LIKE %:search% OR " +
            "s.CPName LIKE %:search% OR " +
            "s.phone LIKE %:search% OR " +
            "s.email LIKE %:search% OR " +
            "s.address LIKE %:search%)")
    Page<SupplierEntity> findAllSearch(Pageable pageable, @Param("search") String searchVal);

    @Query("SELECT s FROM SupplierEntity s WHERE s.deletedAt IS NULL AND (" +
            "s.name LIKE %:search% OR " +
            "s.CPName LIKE %:search% OR " +
            "s.phone LIKE %:search% OR " +
            "s.email LIKE %:search% OR " +
            "s.address LIKE %:search%)")
    List<SupplierEntity> findAllSearch(Sort sort, @Param("search") String searchVal);
}
