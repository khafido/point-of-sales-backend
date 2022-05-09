package com.mitrais.cdcpos.dto;

import com.mitrais.cdcpos.entity.auth.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {
   ERole roles;
}
