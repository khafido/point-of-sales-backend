package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleEntity> generateRole() {
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity(ERole.ROLE_EMPLOYEE));
        roles.add(new RoleEntity(ERole.ROLE_ADMIN));
        roles.add(new RoleEntity(ERole.ROLE_MANAGER));
        roles.add(new RoleEntity(ERole.ROLE_OWNER));
        roles.add(new RoleEntity(ERole.ROLE_CASHIER));
        roles.add(new RoleEntity(ERole.ROLE_STOCKIST));
        return roleRepository.saveAll(roles);
    }

    public List<RoleEntity> getAll(){
        return roleRepository.findAll();
    }
}
