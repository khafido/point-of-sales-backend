package com.mitrais.cdcpos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<InvoiceRepository, UUID> {
}
