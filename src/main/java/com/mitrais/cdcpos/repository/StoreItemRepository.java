package com.mitrais.cdcpos.repository;
import com.mitrais.cdcpos.entity.store.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreItemRepository extends JpaRepository<StoreItemEntity, UUID> {

    @Query(value = "SELECT id, created_at, deleted_at, last_modified_at, stock, item_id, store_id FROM public.store_item " +
            "WHERE store_id = :storeId AND item_id = :itemId", nativeQuery = true)
    Optional<StoreItemEntity> findByStoreIdAndItemId(@Param("storeId") UUID storeId, @Param("itemId") UUID itemId);
}
