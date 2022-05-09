package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mitrais.cdcpos.entity.item.SupplierEntity;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierDto {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cpname;

    public static SupplierDto toDto (SupplierEntity entity) {
        SupplierDto dto = new SupplierDto();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setCpname(entity.getCpname());

        return dto;
    }

}
