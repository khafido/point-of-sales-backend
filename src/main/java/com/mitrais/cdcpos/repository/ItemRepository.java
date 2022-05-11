package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {
    boolean existsByBarcode(String barcode);

    boolean existsByBarcodeAndDeletedAtIsNull(String barcode);

    Page<ItemEntity> findByDeletedAtIsNull(Pageable pageable);

    Optional<ItemEntity> findByIdAndDeletedAtIsNull(UUID id);

    Optional<ItemEntity> findByNameIgnoreCaseAndDeletedAtIsNull(String name);


    @Query("SELECT i FROM ItemEntity i WHERE i.deletedAt IS NULL AND (" +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(i.category.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(i.packaging) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<ItemEntity> findAllSearch(Pageable pageable, @Param("search") String searchVal);



}
