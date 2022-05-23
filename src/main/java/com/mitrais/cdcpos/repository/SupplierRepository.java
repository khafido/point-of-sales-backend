package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.item.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, UUID> {

    @Query("SELECT s FROM SupplierEntity s WHERE s.deletedAt IS NULL AND (" +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.cpname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.phone) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.address) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<SupplierEntity> findAllSearch(Pageable pageable, @Param("search") String searchVal);

    @Query("SELECT s FROM SupplierEntity s WHERE s.deletedAt IS NULL AND (" +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.cpname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.phone) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.address) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<SupplierEntity> findAllSearch(Sort sort, @Param("search") String searchVal);

    Optional<SupplierEntity> findByIdAndDeletedAtIsNull(UUID id);
}
