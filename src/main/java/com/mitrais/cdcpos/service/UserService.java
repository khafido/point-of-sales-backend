package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.AddRoleDto;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.exception.ResourceNotFoundException;
import com.mitrais.cdcpos.repository.RoleRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);


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
//        searchValue = searchValue.toLowerCase();
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
        return UserDto.toDtoMain(user);
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

    public UserEntity getByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        return null;
    }

    public Boolean checkPassword(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            if (encoder.matches(password, user.getPassword()) == true) {
                logger.info("" + user.getUsername() + " Password is correct");
                return true;
            } else {
                logger.info("" + user.getUsername() + " Password is incorrect");
                return false;
            }
        }
        return false;
    }

//    public UserEntity changePassword(String username, String newPassword) {
//        UserEntity user = getByUsername(username);
//        user.setPassword(encoder.encode(newPassword));
//        return userRepository.save(user);
//    }

    public UserEntity addRoles(UUID id, AddRoleDto req){
        UserEntity user = getById(id);
        user.getRoles().clear();
        Set<RoleEntity> roles = req.getRoles().stream()
                .map(r -> roleRepository.findByName(r)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Role Name", r)))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        return userRepository.save(user);
    }

}