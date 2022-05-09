package com.mitrais.cdcpos.controller;


import com.mitrais.cdcpos.dto.AddRoleDto;
import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.repository.UserRepository;
import com.mitrais.cdcpos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public List<UserEntity> getAllUserActive() {
        return userService.getAllUserActive();
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable("id") UUID id) {
        return userService.getActiveUserById(id);
    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> addUser(@RequestBody @Valid UserDto req) {
        try{
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
        boolean user = userService.updateUser(id, req);
        if (user) {
            return new ResponseEntity<>
                    (new GenericResponse(req, "User Updated!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>
                    (new GenericResponse(req, "Update User Failed!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse> updateUserStatus(@PathVariable("id") UUID id) {
        boolean user = userService.deleteUser(id);
        if (user) {
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


//
//    @GetMapping("/all-username")
//    public List<String> getUsername() {
//        return userRepository.findAllUsername();
//    }
//
//    @GetMapping("/all-email")
//    public List<String> getEmail() {
//        return userRepository.findAllEmail();
//    }

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
