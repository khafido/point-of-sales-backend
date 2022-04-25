package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.DetailInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DetailInvoiceRepository extends JpaRepository<DetailInvoiceEntity, UUID> {
}
