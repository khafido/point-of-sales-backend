package com.mitrais.cdcpos.repository;


import com.mitrais.cdcpos.entity.store.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, UUID> {
    @Query("select s from StoreEntity s " +
            "where upper(s.name) like upper('%'||?1||'%') or upper(s.location) like upper('%'||?1||'%') and s.deletedAt is null")
    Page<StoreEntity> search(String searchValue, Pageable pageable);

    @Query("select s from StoreEntity s " +
            "where upper(s.name) like upper('%'||?1||'%') or upper(s.location) like upper('%'||?1||'%') and s.deletedAt is null")
    List<StoreEntity> search(String searchValue, Sort sort);

    Optional<StoreEntity> findByIdEqualsAndDeletedAtIsNull(UUID id);

}
