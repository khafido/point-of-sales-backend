package com.mitrais.cdcpos.dto;

import com.mitrais.cdcpos.entity.auth.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {
   @NotEmpty
   Set<ERole> roles;
}
