package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String photo;
    private LocalDate birthDate;

}
