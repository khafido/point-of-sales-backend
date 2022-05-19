package com.mitrais.cdcpos.controller;

import com.mitrais.cdcpos.dto.GenericResponse;
import com.mitrais.cdcpos.dto.JwtDto;
import com.mitrais.cdcpos.dto.LoginDto;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/auth")
@CrossOrigin(origins="*", maxAge=3600)
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {
        try{
            JwtDto user = authService.login(loginRequest);
            return new ResponseEntity<>
                    (new GenericResponse(user, "Login Success!"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>
                    (new GenericResponse(loginRequest, "Login Failed!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
