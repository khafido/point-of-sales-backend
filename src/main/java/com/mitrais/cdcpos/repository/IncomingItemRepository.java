package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.item.IncomingItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncomingItemRepository extends JpaRepository<IncomingItemEntity, UUID> {
}
