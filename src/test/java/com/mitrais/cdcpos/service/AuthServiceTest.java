package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.JwtDto;
import com.mitrais.cdcpos.dto.LoginDto;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
    }

    @Test
    void login() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("managera");
        loginDto.setPassword("hippos");

        Mockito.when(authService.login(loginDto).getUser()).thenReturn(new UserDto());
        UserDto result = authService.login(loginDto).getUser();

        Mockito.verify(authService).login(loginDto);
    }

    @Test
    void changePassword() {
    }
}