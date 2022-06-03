package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.ChangePasswordDto;
import com.mitrais.cdcpos.dto.JwtDto;
import com.mitrais.cdcpos.dto.LoginDto;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    AuthService authService;

    LoginDto loginDto = new LoginDto();
    JwtDto result;

    @BeforeEach
    void init() {
        loginDto.setUsername("managera");
        loginDto.setPassword("hippos");

        result = authService.login(loginDto);
    }

    @Test
    void login() {
        assertNotNull(result);
        assertEquals(loginDto.getUsername(), result.getUser().getUsername());

        loginDto.setUsername("wrongusername");
        loginDto.setPassword("hippos");
        try {
            result = authService.login(loginDto);
        } catch (BadCredentialsException e) {
            assertEquals(e.getMessage(), "Bad credentials");
        }

        loginDto.setUsername("managera");
        loginDto.setPassword("wrongpassword");
        try {
            result = authService.login(loginDto);
        } catch (BadCredentialsException e) {
            assertEquals(e.getMessage(), "Bad credentials");
        }
    }

    @Test
    void changePassword() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword(loginDto.getPassword());
        changePasswordDto.setNewPassword("hippos");

        boolean result = authService.changePassword(changePasswordDto);
        assertEquals(true, result);

        changePasswordDto.setOldPassword("wrongoldpassword");
        result = authService.changePassword(changePasswordDto);
        assertEquals(false, result);
    }
}