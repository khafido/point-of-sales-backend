package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierRequestDto {

    private String name;
    private String email;
    private String phone;
    private String address;
    private String CPName;

}
