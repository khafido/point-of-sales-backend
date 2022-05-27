package com.mitrais.cdcpos.dto.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.dto.UserDto;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDto {

    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String location;

    private UserDto manager;

    private int totalEmployee;

    public static StoreDto toDtoWithTotalEmployee(StoreEntity entity, int totalEmployee) {
        StoreDto dto = new StoreDto();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setTotalEmployee(totalEmployee);
        dto.setManager(entity.getManager()!=null? UserDto.toDtoMain(entity.getManager()) : null);
        return dto;
    }

    public static StoreDto toDto(StoreEntity entity) {
        StoreDto dto = new StoreDto();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setManager(entity.getManager()!=null? UserDto.toDtoMain(entity.getManager()) : null);
        return dto;
    }

    public static StoreDto toDtoWithoutManager(StoreEntity entity) {
        StoreDto dto = new StoreDto();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        return dto;
    }
}
