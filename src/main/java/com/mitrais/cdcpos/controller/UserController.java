package com.mitrais.cdcpos.controller;


import com.mitrais.cdcpos.dto.AddRoleDto;
import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.PaginatedDto;
import com.mitrais.cdcpos.dto.UserDto;

import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import com.mitrais.cdcpos.repository.RoleRepository;

import com.mitrais.cdcpos.repository.UserRepository;
import com.mitrais.cdcpos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAllUserActive(
        @RequestParam(defaultValue = "false") Boolean isPaginated,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "") String searchValue,
        @RequestParam(defaultValue = "default") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        try {
            Page<UserEntity> items = userService.getAllUserActivePage(isPaginated, page, size, searchValue, sortBy, sortDirection);
            List<UserDto> itemsDto = items.getContent().stream().map(UserDto::toDtoMain).collect(Collectors.toList());
            PaginatedDto<UserDto> result = new PaginatedDto<>(
                    itemsDto,
                    items.getNumber(),
                    items.getTotalPages()
            );
            return new ResponseEntity<>(new GenericResponse(result, "Get User Success", GenericResponse.Status.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GenericResponse(null, e.getMessage(), GenericResponse.Status.ERROR_INTERNAL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable("id") UUID id) {
        return userService.getActiveUserById(id);
    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> addUser(@RequestBody @Valid UserDto req) {
        try{
            req.setPassword(encoder.encode(req.getUsername()));

            UserEntity user = userService.addUser(req);
            return new ResponseEntity<>
                    (new GenericResponse(req, "User Created!"), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Create User Failed!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserDto req) {
        UserEntity user = userService.updateUser(id, req);
        if (user!=null) {
            return new ResponseEntity<>
                    (new GenericResponse(req, "User Updated!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>
                    (new GenericResponse(req, "Update User Failed!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> updateUserStatus(@PathVariable("id") UUID id) {
        UserEntity user = userService.deleteUser(id);
        if (user!=null) {
            return new ResponseEntity<>
                    (new GenericResponse("User Deleted!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>
                    (new GenericResponse("Delete User Failed!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-username/{username}/")
    public boolean checkUsername(@PathVariable("username") String username) {
        return userService.isUsernameExist(username);
    }

    @GetMapping("/check-email/{email}/")
    public boolean checkEmail(@PathVariable("email") String email) {
        return userService.isEmailExist(email);
    }

//    @GetMapping("/generate-role")
//    public List<RoleEntity> generateRole() {
//        return userService.generateRole();
//    }
//
    @GetMapping("/generate")
    public List<UserEntity> generateAllUsers() {
        return userService.generateUsers();
    }


    @PatchMapping("{id}/add-roles")
    public ResponseEntity<GenericResponse> addRoles(@PathVariable("id") UUID id, @RequestBody AddRoleDto req){
        var user = userService.addRoles(id, req);
        return new ResponseEntity<>
                (new GenericResponse(user, "Role "+ req.getRoles()+" added", GenericResponse.Status.SUCCESS), HttpStatus.OK);
    }

    @PatchMapping("{id}/remove-roles")
    public ResponseEntity<GenericResponse> removeRoles(@PathVariable("id")UUID id, @RequestBody AddRoleDto req){
        var user = userService.removeRoles(id,req);
        return new ResponseEntity<>
                (new GenericResponse(user,"Role "+ req.getRoles()+" removed", GenericResponse.Status.SUCCESS), HttpStatus.OK);
    }

}
