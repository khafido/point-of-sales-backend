package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.dto.store.StoreDto;
import com.mitrais.cdcpos.entity.store.StoreEmployeeEntity;
import com.mitrais.cdcpos.entity.store.StoreEntity;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreEmployeeDto {
    private String id;
    private StoreDto store;
    private UserDto user;

    public static StoreEmployeeDto toDto(StoreEmployeeEntity entity) {
        StoreEmployeeDto dto = new StoreEmployeeDto();
        dto.setId(entity.getId().toString());
        dto.setStore(StoreDto.toDtoWithoutManager(entity.getStore()));
        return dto;
    }

    public static StoreEmployeeDto toDtoWithUser(StoreEmployeeEntity entity) {
        StoreEmployeeDto dto = new StoreEmployeeDto();
        dto.setId(entity.getId().toString());
        dto.setUser(UserDto.toDtoMain(entity.getUser()));
        dto.setStore(StoreDto.toDtoWithoutManager(entity.getStore()));
        return dto;
    }
}
