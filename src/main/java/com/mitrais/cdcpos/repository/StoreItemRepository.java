package com.mitrais.cdcpos.repository;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
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
public interface StoreItemRepository extends JpaRepository<StoreItemEntity, UUID> {

    @Query(value = "SELECT si FROM StoreItemEntity si " +
            "WHERE si.store.id = :storeId AND si.item.id = :itemId")
    Optional<StoreItemEntity> findByStoreIdAndItemId(@Param("storeId") UUID storeId, @Param("itemId") UUID itemId);

    @Query(value = "SELECT si FROM StoreItemEntity si " +
            "WHERE si.store.id = :storeId AND (" +
            "LOWER(si.priceMode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.barcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.category.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.packaging) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<StoreItemEntity> findByStoreIdWithSearch(Pageable pageable, @Param("storeId") UUID storeId, @Param("search") String searchVal);

    @Query(value = "SELECT si FROM StoreItemEntity si " +
            "WHERE si.store.id = :storeId AND (" +
            "LOWER(si.priceMode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.barcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.category.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.packaging) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(si.item.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<StoreItemEntity> findByStoreIdWithSearch(Sort sort, @Param("storeId") UUID storeId, @Param("search") String searchVal);
}
