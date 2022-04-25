package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

}
