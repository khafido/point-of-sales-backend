package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.entity.auth.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String photo;
    private LocalDate birthDate;
    private List<RoleDto> roles;
    private StoreDto managerAt;

    public UserDto(UUID id, String username, String firstName, String lastName, String email, List<RoleDto> roles, StoreDto managerAt) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.managerAt = managerAt;
    }

    public UserDto(UUID id, String username, String firstName, String lastName, String email, String phone, String address, String gender, String photo, LocalDate birthDate, List<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.photo = photo;
        this.birthDate = birthDate;
        this.roles = roles;
    }

    public static UserDto toDtoMain(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getGender(),
                entity.getPhoto(),
                entity.getBirthDate(),
                entity.getRoles().stream().map(RoleDto::toDto).collect(Collectors.toList())
        );
    }

    public static UserDto toDtoStore(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getRoles().stream().map(RoleDto::toDto).collect(Collectors.toList()),
                StoreDto.toDtoWithoutManager(entity.getStoreManager())
        );
    }
}
