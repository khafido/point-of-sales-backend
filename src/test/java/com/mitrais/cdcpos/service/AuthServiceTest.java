package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.JwtDto;
import com.mitrais.cdcpos.dto.LoginDto;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.repository.UserRepository;
import com.mitrais.cdcpos.security.jwt.JwtUtils;
import com.mitrais.cdcpos.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    LoginDto loginDto;
    UserDto userDto;

    @BeforeEach
    void init() {
        loginDto = new LoginDto();
        loginDto.setUsername("managera");
        loginDto.setPassword("hippos");

        userDto = new UserDto();
        userDto.setUsername("managera");
        userDto.setPassword("hippos");
        userDto.setFirstName("Manager");
        userDto.setLastName("A");

    }

    @Test
    void login() {
        when(userRepository.findByUsername("managera")).thenReturn(userDto);
        UserDto result = authService.login(loginDto).getUser();
    }

    @Test
    void changePassword() {

    }
}