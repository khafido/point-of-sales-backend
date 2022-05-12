package com.mitrais.cdcpos.service;

import com.mitrais.cdcpos.dto.AddRoleDto;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import com.mitrais.cdcpos.repository.RoleRepository;
import com.mitrais.cdcpos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private List<UserEntity> userList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userList = Arrays.asList(
          Mockito.mock(UserEntity.class),
          Mockito.mock(UserEntity.class)
        );
    }

    @Test
    void getAllUserActive() {
        Sort sortAsc = Sort.by("firstName").ascending();
        Sort sortDesc = Sort.by("firstName").descending();

        Mockito.when(userRepository.findAllSearch(sortAsc, "unitTest")).thenReturn(userList);
        Mockito.when(userRepository.findAllSearch(sortDesc, "unitTest")).thenReturn(userList);

        Page<UserEntity> resultAsc = userService.getAllUserActivePage(false, 0, 0, "unitTest", "firstName", "asc");
        Page<UserEntity> resultDesc = userService.getAllUserActivePage(false, 0, 0, "unitTest", "firstName", "desc");

        assertArrayEquals(userList.toArray(), resultAsc.getContent().toArray());
        assertArrayEquals(userList.toArray(), resultDesc.getContent().toArray());
    }

    @Test
    void getAllUserActivePage() {
        Sort sortAsc = Sort.by("firstName").ascending();
        Sort sortDesc = Sort.by("firstName").descending();

        Pageable pageAsc = PageRequest.of(0, 10, sortAsc);
        Pageable pageDesc = PageRequest.of(0, 10, sortDesc);

        Page<UserEntity> pageUsersAsc = new PageImpl<>(userList, pageAsc, 2);
        Page<UserEntity> pageUsersDesc = new PageImpl<>(userList, pageDesc, 2);

        Mockito.when(userRepository.findAllSearch(pageAsc, "unitTest")).thenReturn(pageUsersAsc);
        Mockito.when(userRepository.findAllSearch(pageDesc, "unitTest")).thenReturn(pageUsersDesc);

        Page<UserEntity> resultAsc = userService.getAllUserActivePage(true, 0, 10, "unitTest", "firstName", "asc");
        Page<UserEntity> resultDesc = userService.getAllUserActivePage(true, 0, 10, "unitTest", "firstName", "desc");

        assertArrayEquals(userList.toArray(), resultAsc.getContent().toArray());
        assertArrayEquals(userList.toArray(), resultDesc.getContent().toArray());
        Mockito.verify(userRepository, Mockito.times(1)).findAllSearch(pageAsc, "unitTest");
        Mockito.verify(userRepository, Mockito.times(1)).findAllSearch(pageDesc, "unitTest");
    }

    @Test
    void addUser() {
        UserDto reqDto = Mockito.mock(UserDto.class);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userList.get(0));

        UserEntity result = userService.addUser(reqDto);

        assertEquals(userList.get(0), result);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    void updateUser() {
        UUID id = UUID.randomUUID();
        UserDto reqDto = Mockito.mock(UserDto.class);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.ofNullable(userList.get(0)));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userList.get(1));

        UserEntity result = userService.updateUser(id, reqDto);
        assertEquals(userList.get(1), result);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void deleteUser() {
        UUID id = UUID.randomUUID();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.ofNullable(userList.get(0)));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userList.get(1));

        UserEntity result = userService.deleteUser(id);

        assertEquals(userList.get(1), result);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void isUsernameExist() {
        Mockito.when(userRepository.existsByUsername("unitTest")).thenReturn(false);
        boolean resultFalse = userService.isUsernameExist("unitTest");
        assertFalse(resultFalse);

        Mockito.when(userRepository.existsByUsername("khafido")).thenReturn(true);
        boolean resultTrue = userService.isUsernameExist("khafido");
        assertTrue(resultTrue);
    }

    @Test
    void isEmailExist() {
        Mockito.when(userRepository.existsByEmail("user@email.com")).thenReturn(false);
        boolean resultFalse = userService.isEmailExist("user@email.com");
        assertFalse(resultFalse);

        Mockito.when(userRepository.existsByEmail("khafido@email.com")).thenReturn(true);
        boolean resultTrue = userService.isEmailExist("khafido@email.com");
        assertTrue(resultTrue);
    }


    @Test()
    void addRole(){
        // Given
        RoleEntity role = new RoleEntity();
        role.setName(ERole.ROLE_ADMIN);
        UserEntity user = new UserEntity();
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        Mockito.when(userRepository.findByIdAndDeletedAtIsNull(user.getId())).thenReturn(user);
        Mockito.when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(user);

        // When
        AddRoleDto req = new AddRoleDto();
        req.setRoles(Set.of(ERole.ROLE_ADMIN));
        UserEntity result = userService.addRoles(user.getId(),req);

        // Then
        assertEquals(user, result);
        Mockito.verify(userRepository).findByIdAndDeletedAtIsNull(user.getId());
        Mockito.verify(roleRepository).findByName(role.getName());
        Mockito.verify(userRepository).save(user);
    }
}