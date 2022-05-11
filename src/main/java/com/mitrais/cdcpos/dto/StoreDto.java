package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
