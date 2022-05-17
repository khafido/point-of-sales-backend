package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.store.StoreEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StoreEmployeeRepository extends JpaRepository<StoreEmployeeEntity, UUID> {
    @Query("select s from StoreEmployeeEntity s where s.store.id = ?1")
    List<StoreEmployeeEntity> findByStore_IdEquals(UUID id);

}
