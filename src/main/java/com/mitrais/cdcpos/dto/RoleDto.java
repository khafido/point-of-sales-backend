package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.entity.auth.ERole;
import com.mitrais.cdcpos.entity.auth.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    public int id;
    public String name;
    public List<UserDto> users;

    public RoleDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RoleDto toDtoWithUsers(RoleEntity entity) {
        return new RoleDto(
                entity.getId(),
                entity.getName().toString(),
                entity.getName().equals(ERole.ROLE_MANAGER) ?
                entity.getUsers().stream().map(UserDto::toDtoStore).collect(Collectors.toList()) :
                entity.getUsers().stream().map(UserDto::toDtoEmployee).collect(Collectors.toList())
        );
    }

    public static RoleDto toDto(RoleEntity entity) {
        return new RoleDto(
                entity.getId(),
                entity.getName().toString()
        );
    }
}
