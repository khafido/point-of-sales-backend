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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    AuthService authService;

    LoginDto loginDto = new LoginDto();

    @BeforeEach
    void init() {
        loginDto.setUsername("managera");
        loginDto.setPassword("hippos");

        authService.login(loginDto);
    }

    @Test
    void login() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("managera");
        loginDto.setPassword("hippos");

        JwtDto result = authService.login(loginDto);

        assertNotNull(result);
        assertEquals(loginDto.getUsername(), result.getUser().getUsername());
    }

    @Test
    void changePassword() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword(loginDto.getPassword());
        changePasswordDto.setNewPassword("hippos");

        boolean result = authService.changePassword(changePasswordDto, loginDto.getUsername());
        assertEquals(true, result);
    }
}