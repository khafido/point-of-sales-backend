package com.mitrais.cdcpos.controller;


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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public List<UserEntity> getAllUserActive() {
        return userService.getAllUserActive();
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse> addUser(@RequestBody @Valid UserDto req) {
        try{
            UserEntity user = userService.addUser(req);
            return new ResponseEntity<>
                    (new GenericResponse(user, "User Created!"), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>
                    (new GenericResponse(req, "Create User Failed!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
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


    @GetMapping("/check-username/{username}/")
    public boolean checkUsername(@PathVariable("username") String username) {
        return userService.isUsernameExist(username);
    }

    @GetMapping("/check-email/{email}/")
    public boolean checkEmail(@PathVariable("email") String email) {
        return userService.isEmailExist(email);
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
}
