package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.store.StoreEmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StoreEmployeeRepository extends JpaRepository<StoreEmployeeEntity, UUID> {

    @Query("select s from StoreEmployeeEntity s " +
            "where (s.deletedAt is null and s.store.id = ?1) and (upper(s.user.firstName) like upper('%'||?2||'%') or upper(s.user.lastName) like upper('%'||?2||'%'))")
    Page<StoreEmployeeEntity> search(UUID id, String searchValue, Pageable pageable);

    @Query("select s from StoreEmployeeEntity s " +
            "where (s.deletedAt is null and s.store.id = ?1) and (upper(s.user.firstName) like upper('%'||?2||'%') or upper(s.user.lastName) like upper('%'||?2||'%'))")
    List<StoreEmployeeEntity> search(UUID id, String searchValue, Sort sort);


    boolean existsByUser_IdEqualsAndStore_IdEquals(UUID id, UUID id1);

    @Query("select s from StoreEmployeeEntity s where s.user.id = ?1")
    StoreEmployeeEntity findByUser_Id(UUID id);


}