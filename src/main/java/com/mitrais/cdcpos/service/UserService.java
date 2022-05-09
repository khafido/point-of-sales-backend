package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.AddRoleDto;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.RoleRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public UserEntity add(UserEntity user) {
        return userRepository.save(user);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserEntity> getAllUserActive() {
        return  userRepository.findByDeletedAtIsNull();
    }

    public UserDto getActiveUserById(UUID id) {
        UserEntity user = userRepository.findByIdAndDeletedAtIsNull(id);
        UserDto result = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getGender(),
                user.getPhoto(),
                user.getBirthDate()
        );
        return result;
    }

    public UserEntity addUser(UserDto req) {
        UserEntity user = new UserEntity();

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_EMPLOYEE).orElse(null));

        user.setRoles(roles);
        user.setUsername(req.getUsername());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setBirthDate(req.getBirthDate());
        user.setPassword(encoder.encode(req.getUsername()));
        user.setAddress(req.getAddress());
        user.setGender(req.getGender());

        if (req.getPhoto() != null) {
            user.setPhoto(req.getPhoto());
        }
        return userRepository.save(user);
    }

    public boolean updateUser(UUID id, UserDto req) {
        UserEntity user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setPhone(req.getPhone());
            user.setBirthDate(req.getBirthDate());
            user.setAddress(req.getAddress());
            user.setGender(req.getGender());
            if (req.getPhoto() != null) {
                user.setPhoto(req.getPhoto());
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDeletedAt(LocalDateTime.now());
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }






    public List<UserEntity> generateUsers() {
        List<UserEntity> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserEntity user = new UserEntity("newuser" + i, "User", i+"", "user" + i + "@email.com", "08564823648"+i, LocalDate.now());
            user.setPassword(encoder.encode("user" + i));
            user.setAddress("Jl. Ahmad Yani No. " + i);
            if (i % 2 == 0) {
                user.setGender("Male");
            } else {
                user.setGender("Female");
            }
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_EMPLOYEE).orElse(null));
            user.setRoles(roles);

            users.add(user);
        }
        userRepository.saveAll(users);
        return users;
    }

    public List<RoleEntity> generateRole() {
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity(ERole.ROLE_EMPLOYEE));
        roles.add(new RoleEntity(ERole.ROLE_ADMIN));
        roles.add(new RoleEntity(ERole.ROLE_MANAGER));
        roles.add(new RoleEntity(ERole.ROLE_OWNER));
        roles.add(new RoleEntity(ERole.ROLE_CASHIER));
        roles.add(new RoleEntity(ERole.ROLE_STOCKIST));
        return roleRepository.saveAll(roles);
    }

    public UserEntity getByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return userEntity;
        }
        return null;
    }

    public Boolean checkPassword(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            if (encoder.matches(password, userEntity.getPassword()) == true) {
                logger.info("" + userEntity.getUsername() + " Password is correct");
                return true;
            } else {
                logger.info("" + userEntity.getUsername() + " Password is incorrect");
                return false;
            }
        }
        return false;
    }

    public UserEntity changePassword(String username, String newPassword) {
        UserEntity user = getByUsername(username);
        user.setPassword(encoder.encode(newPassword));
        return userRepository.save(user);
    }

    public UserEntity addRoles(UUID id, AddRoleDto req){
        UserEntity user = getById(id);
        RoleEntity role= roleRepository.findByName(req.getRoles())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Role Name",req.getRoles()));
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public UserEntity removeRoles(UUID id, AddRoleDto req){
        UserEntity user = getById(id);
        RoleEntity role= roleRepository.findByName(req.getRoles())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Role Name",req.getRoles()));
        user.getRoles().remove(role);
        return userRepository.save(user);
    }
}