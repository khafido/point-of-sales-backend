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

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll(){
        try{
            List<RoleEntity> roleEntityList = roleService.getAll();
            return new ResponseEntity<>
                    (new GenericResponse(roleEntityList,"Get roles success",
                            GenericResponse.Status.SUCCESS), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>
                    (new GenericResponse(null,e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getRoleById(@PathVariable int id) {
        try {
            RoleEntity result = roleService.getRoleById(id);
            if (result != null) {
                RoleDto resultDto = RoleDto.toDtoWithUsers(result);
                return new ResponseEntity<>(new GenericResponse(resultDto, "Get Role Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
            }
            return new ResponseEntity<>(new GenericResponse(null, "Role ID Not Found", GenericResponse.Status.ERROR_NOT_FOUND), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
