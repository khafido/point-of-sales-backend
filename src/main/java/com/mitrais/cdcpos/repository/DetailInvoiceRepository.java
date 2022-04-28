package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.DetailInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DetailInvoiceRepository extends JpaRepository<DetailInvoiceEntity, UUID> {
}
