package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleEntity> getAll(){
        return roleRepository.findAll();
    }

    public RoleEntity getRoleById(int id) {
        Optional<RoleEntity> result =  roleRepository.findById(id);
        return result.orElse(null);
    }
}
