package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto {
    @NotEmpty
    String name;
    @NotEmpty
    int value;

}
