package com.mitrais.cdcpos.dto;

import com.mitrais.cdcpos.entity.auth.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RoleDto {

    public int id;
    public String name;
    public List<UserDto> users;

    public static RoleDto toDto (RoleEntity entity) {
        return new RoleDto(
                entity.getId(),
                entity.getName().toString(),
                entity.getUsers().stream().map(UserDto::toDto).collect(Collectors.toList())
        );
    }
}
