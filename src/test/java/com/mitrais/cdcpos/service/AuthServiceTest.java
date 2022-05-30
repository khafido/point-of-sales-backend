package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.LoginDto;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

//@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

//    @Mock
//    AuthenticationManager authenticationManager;
//
//    @Mock
//    JwtUtils jwtUtils;
//
//    @Mock
//    UserDetailsImpl userDetailsImpl;
//
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
    }

    @Test
    void login() {
    }

    @Test
    void changePassword() {
    }
}