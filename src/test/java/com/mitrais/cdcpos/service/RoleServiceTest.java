package com.mitrais.cdcpos.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RoleService.class})
@ExtendWith(SpringExtension.class)
class RoleServiceTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Test
    void testGetAll() {
        ArrayList<RoleEntity> roleEntityList = new ArrayList<>();
        when(this.roleRepository.findAll()).thenReturn(roleEntityList);
        List<RoleEntity> actualAll = this.roleService.getAll();
        assertSame(roleEntityList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(this.roleRepository).findAll();
    }

    @Test
    void testGetRoleById() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setName(ERole.ROLE_ADMIN);
        roleEntity.setUsers(new ArrayList<>());
        Optional<RoleEntity> ofResult = Optional.of(roleEntity);
        when(this.roleRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(roleEntity, this.roleService.getRoleById(1));
        verify(this.roleRepository).findById((Integer) any());
    }
}

