package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.item.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {
    boolean existsByName(String name);

    Page<ItemEntity> findByDeletedAtIsNull(Pageable pageable);

    Optional<ItemEntity> findByIdAndDeletedAtIsNull(UUID id);

    Optional<ItemEntity> findByNameAndDeletedAtIsNull(String name);

}
