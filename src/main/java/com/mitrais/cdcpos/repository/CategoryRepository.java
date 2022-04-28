package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.CategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    boolean existsByName(String name);
    List<CategoryEntity> findByDeletedAtIsNull();

}
