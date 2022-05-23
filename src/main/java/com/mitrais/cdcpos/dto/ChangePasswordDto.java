package com.mitrais.cdcpos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    @JsonProperty("old")
    private String oldPassword;
    @JsonProperty("new")
    private String newPassword;
    @JsonProperty("confirmation")
    private String confirmPassword;
}
