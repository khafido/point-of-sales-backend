package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.AddRoleDto;
import com.mitrais.cdcpos.dto.RoleDto;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.RoleRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAll() {
        return userRepository.findByDeletedAtIsNull();
    }

    public UserEntity getById(UUID id) {
        return userRepository.findByIdAndDeletedAtIsNull(id);
    }

    public Page<UserEntity> getAllUserActivePage(
            boolean paginated,
            int page,
            int size,
            String searchValue,
            String sortBy,
            String sortDirection
    ) {
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("lastName").ascending());

        Pageable paging = null;
        Page<UserEntity> result = null;

        if (!sortBy.equalsIgnoreCase("default")) {
            if("DESC".equalsIgnoreCase(sortDirection)) {
                sort = Sort.by(sortBy).descending();
            } else {
                sort = Sort.by(sortBy).ascending();
            }
        }

        if(paginated) {
            paging = PageRequest.of(page, size, sort);
//            paging = PageRequest.of(page, size);
            result = userRepository.findAllSearch(paging, searchValue);
        } else {
            List<UserEntity> entityList = userRepository.findAllSearch(sort, searchValue);
//            List<UserEntity> entityList = userRepository.findAllSearch(searchValue);
            result = new PageImpl<>(entityList);
        }

        return result;
    }

    public UserDto getActiveUserById(UUID id) {
        UserEntity user = userRepository.findByIdAndDeletedAtIsNull(id);
        return UserDto.toDto(user);
    }

    public UserEntity addUser(UserDto req) {
        UserEntity user = new UserEntity();

        RoleEntity role = new RoleEntity(1, ERole.ROLE_EMPLOYEE);
        Set<RoleEntity> roles = new HashSet<>();
        try{
            roles.add(roleRepository.findByName(ERole.ROLE_EMPLOYEE).orElse(role));
        } catch (NullPointerException e) {
            roles.add(role);
        }

        user.setRoles(roles);
        user.setUsername(req.getUsername());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setPhone(req.getPhone());
        user.setBirthDate(req.getBirthDate());
        user.setAddress(req.getAddress());
        user.setGender(req.getGender());

        if (req.getPhoto() != null) {
            user.setPhoto(req.getPhoto());
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(UUID id, UserDto req) {
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
            return userRepository.save(user);
        }
        return null;
    }

    public UserEntity deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDeletedAt(LocalDateTime.now());
            return userRepository.save(user);
        } else {
            return null;
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