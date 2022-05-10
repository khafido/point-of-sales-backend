package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.RoleDto;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getRoleById(@PathVariable int id) {
        try {
            RoleEntity result = roleService.getRoleById(id);
            if (result != null) {
                RoleDto resultDto = RoleDto.toDto(result);
                return new ResponseEntity<>(new GenericResponse(resultDto, "Get Role Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new GenericResponse(null, "Role ID Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
