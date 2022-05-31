package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncomingItemRepository extends JpaRepository<IncomingItemEntity, UUID> {

    @Query("SELECT i FROM IncomingItemEntity i WHERE i.storeItem.store.id = :storeId AND i.storeItem.item.id = :itemId")
    List<IncomingItemEntity> findByStoreIdAndItemId(Pageable page, @Param("storeId") UUID storeId, @Param("itemId") UUID itemId);

    @Query("select i from IncomingItemEntity i where cast(i.buyDate as date) between cast(:start as date) and cast(:end as date) and " +
            "((lower(i.storeItem.item.name) like lower(concat('%', :search, '%'))) or " +
            "(lower(i.supplier.name) like lower(concat('%', :search, '%'))))")
    Page<IncomingItemEntity> findAllSearch(Pageable pageable, @Param("search") String search,
                                           @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select i from IncomingItemEntity i where cast(i.buyDate as date) between cast(:start as date) and cast(:end as date) and " +
            "((lower(i.storeItem.item.name) like lower(concat('%', :search, '%'))) or " +
            "(lower(i.supplier.name) like lower(concat('%', :search, '%'))))")
    List<IncomingItemEntity> findAllSearch(Sort sort,@Param("search") String search,
                                           @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select i from IncomingItemEntity i where i.storeItem.store.id = :storeId and " +
            "i.expiryDate <= current_timestamp and " +
            "((lower(i.storeItem.item.name) like lower(concat('%', :search, '%'))) or " +
            "(lower(i.supplier.name) like lower(concat('%', :search, '%')))) and cast(i.createdAt as date) between cast(:start as date) and cast(:end as date)")
    Page<IncomingItemEntity> findAllExpired(Pageable pageable, @Param("storeId") UUID storeId,@Param("search") String search,
                                            @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select i from IncomingItemEntity i where i.storeItem.store.id = :storeId and " +
            "i.expiryDate <= current_timestamp and " +
            "((lower(i.storeItem.item.name) like lower(concat('%', :search, '%'))) or " +
            "(lower(i.supplier.name) like lower(concat('%', :search, '%')))) and cast(i.createdAt as date) between cast(:start as date) and cast(:end as date)")
    List<IncomingItemEntity> findAllExpired(Sort sort, @Param("storeId") UUID storeId,@Param("search") String search,
                                            @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
