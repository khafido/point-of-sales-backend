package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.*;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.entity.auth.UserRoleEntity;
import com.mitrais.cdcpos.repository.*;
import com.mitrais.cdcpos.security.jwt.JwtUtils;
import com.mitrais.cdcpos.security.services.UserDetailsImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StoreEmployeeRepository storeEmployeeRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public String getLoggedUsername() {
        String username = "hippos";
        try {
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = user.getUsername();
        } catch (ClassCastException err) {
            logger.error("No Authorization / User does not exist!");
        }

        return username;
    }

    public JwtDto login(LoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserDto user = userService.getActiveUserById(userDetails.getId());
        UUID storeIdEmployee = null;
        UUID storeIdManager = null;

        if (roles.contains(ERole.ROLE_CASHIER.toString()) || roles.contains(ERole.ROLE_STOCKIST.toString())) {
            storeIdEmployee = storeEmployeeRepository.findByUser_Id(userDetails.getId()).getStore().getId();
        }

        if (roles.contains(ERole.ROLE_MANAGER.toString())) {
            storeIdManager = storeRepository.findByManager_Id(userDetails.getId()).getId();
        }

        return new JwtDto(jwt, user, storeIdEmployee, storeIdManager, roles);

//        return new JwtDto(jwt,
//                userDetails.getId(),
//                userDetails.getUsername(),
//                userDetails.getEmail(),
//                roles);
    }

    public boolean changePassword(ChangePasswordDto req) {
        UserDto user = userRepository.findByUsername(getLoggedUsername());

        if (encoder.matches(req.getOldPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(req.getNewPassword()));
            UserEntity userEntity = new UserEntity();
            userEntity.setId(user.getId());
            userEntity.setPassword(user.getPassword());

            userRepository.save(userEntity);
            return true;
        } else {
            return false;
        }
    }

//    public ResponseEntity<GenericResponse> register(SignUpDto signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GenericResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new GenericResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        UserEntity user = new UserEntity(signUpRequest.getUsername(),
//                encoder.encode(signUpRequest.getUsername()),
//                signUpRequest.getEmail());
//        user.setBirthDate(signUpRequest.getBirthDate());
//        user.setFirstName(signUpRequest.getFirstName());
//        user.setLastName(signUpRequest.getLastName());
//        user.setAddress(signUpRequest.getAddress());
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<RoleEntity> roles = new HashSet<>();
//
//        // RoleEntity userRole = roleRepository.findByName(ERole.STAFF)
//        // .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//        // roles.add(userRole);
//
//        strRoles.forEach(role -> {
//            switch (role.toLowerCase()) {
//                case "admin":
//                    RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(adminRole);
//                    break;
//                case "cashier":
//                    RoleEntity cashierRole = roleRepository.findByName(ERole.ROLE_CASHIER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(cashierRole);
//                    break;
//                case "stockist":
//                    RoleEntity stockistRole = roleRepository.findByName(ERole.ROLE_STOCKIST)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(stockistRole);
//                    break;
//                case "manager":
//                    RoleEntity managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(managerRole);
//                    break;
//                case "owner":
//                    RoleEntity ownerRole = roleRepository.findByName(ERole.ROLE_OWNER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(ownerRole);
//                    break;
//            }
//        });
//
//        if (roles.isEmpty()) {
//            RoleEntity employeeRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is empty."));
//            roles.add(employeeRole);
//        }
//
//        user.setRoles(roles);
//
//        List<UserRoleEntity> userRoleEntity = new ArrayList<>();
//        roles.forEach(r -> {
//            userRoleEntity.add(new UserRoleEntity(user.getId(), r.getId()));
//        });
//
//        userRoleRepository.saveAll(userRoleEntity);
//        return ResponseEntity.ok(new GenericResponse("Sign Up Successfully!"));
//    }
}
