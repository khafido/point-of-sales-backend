package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoucherRepository extends JpaRepository<VoucherEntity, UUID> {

}
