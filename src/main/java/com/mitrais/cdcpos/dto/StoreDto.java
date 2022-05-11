package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String location;

    private UserDto manager;

    public static StoreDto toDto(StoreEntity entity) {
        StoreDto dto = new StoreDto();
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setManager(UserDto.toDto(entity.getManager()));
        return dto;
    }

    public static StoreDto toDtoWithoutManager(StoreEntity entity) {
        StoreDto dto = new StoreDto();
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        return dto;
    }
}
