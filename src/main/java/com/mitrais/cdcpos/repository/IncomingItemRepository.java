package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.dto.IncomingItemDto;
import com.mitrais.cdcpos.dto.IncomingItemResponseDto;
import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomingItemRepository extends JpaRepository<IncomingItemEntity, UUID> {

    @Query("SELECT i FROM IncomingItemEntity i WHERE i.storeItem.store.id = :storeId AND i.storeItem.item.id = :itemId ORDER BY i.buyDate DESC")
    List<IncomingItemEntity> latestIncomingByStoreIdAndItemId(Pageable page, @Param("storeId") UUID storeId, @Param("itemId") UUID itemId);

    @Query("select i from IncomingItemEntity i where i.buyDate between :start and :end and " +
            "(lower(i.storeItem.item.name) like lower(concat('%', :search, '%'))) or " +
            "(lower(i.supplier.name) like lower(concat('%', :search, '%')))")
    Page<IncomingItemEntity> findAllSearch(Pageable pageable, @Param("search") String search,
                                           @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select i from IncomingItemEntity i where i.buyDate between :start and :end and " +
            "(lower(i.storeItem.item.name) like lower(concat('%', :search, '%'))) or " +
            "(lower(i.supplier.name) like lower(concat('%', :search, '%')))")
    List<IncomingItemEntity> findAllSearch(Sort sort,@Param("search") String search,
                                           @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
