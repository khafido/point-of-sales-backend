package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/role")
@CrossOrigin(origins="*", maxAge=3600)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/generate")
    public List<RoleEntity> addRoles(){
        return roleService.generateRole();
    }

    @GetMapping("")
    public List<RoleEntity> getAll(){
        return roleService.getAll();
    }
}
