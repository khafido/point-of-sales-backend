package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    boolean existsByName(String name);

    Page<CategoryEntity> findByDeletedAtIsNull(Pageable pageable);

    Optional<CategoryEntity> findByIdAndDeletedAtIsNull(UUID id);

    Optional<CategoryEntity> findByNameAndDeletedAtIsNull(String name);

    Optional<CategoryEntity> findByName(String name);

    @Query("select c from CategoryEntity c where c.deletedAt is null and " +
            "c.name like %:search%")
    Page<CategoryEntity> findAllSearch(Pageable pageable, @Param("search") String val);

    @Query("select c from CategoryEntity c where c.deletedAt is null and " +
            "c.name like %:search%")
    List<CategoryEntity> findAllSearch(Sort sort, @Param("search") String val);

}
