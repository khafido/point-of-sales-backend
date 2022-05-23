package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
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
public interface IncomingItemRepository extends JpaRepository<IncomingItemEntity, UUID> {

    @Query("SELECT i FROM IncomingItemEntity i WHERE i.storeItem.store.id = :storeId AND i.storeItem.item.id = :itemId ORDER BY i.buyDate DESC")
    List<IncomingItemEntity> latestIncomingByStoreIdAndItemId(Pageable page, @Param("storeId") UUID storeId, @Param("itemId") UUID itemId);



}
