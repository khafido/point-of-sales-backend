package com.mitrais.cdcpos.repository;

import com.mitrais.cdcpos.entity.ParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.tags.Param;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParameterRepository extends JpaRepository<ParameterEntity, UUID> {

    Optional<ParameterEntity> findByNameIgnoreCase(String name);
}
