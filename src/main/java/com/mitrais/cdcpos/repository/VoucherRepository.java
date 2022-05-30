package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.CategoryEntity;
import com.mitrais.cdcpos.entity.VoucherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, UUID> {
    boolean existsByCode(String code);
    @Query("select v from VoucherEntity v where v.deletedAt is null and " +
            "(lower(v.name) like lower(concat('%', :search, '%')))")
    Page<VoucherEntity> findAllSearch(Pageable pageable, @Param("search") String val);

    @Query("select v from VoucherEntity v where v.deletedAt is null and " +
            "(lower(v.name) like lower(concat('%', :search, '%')))")
    List<VoucherEntity> findAllSearch(Sort sort, @Param("search") String val);
}
