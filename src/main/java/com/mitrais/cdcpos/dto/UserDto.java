package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public static UserDto toDto (UserEntity entity) {
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

}
