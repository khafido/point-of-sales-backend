package com.mitrais.cdcpos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {
    private String token;
    private String type = "Bearer";
//    private UUID id;
//    private String username;
//    private String email;
    private List<String> roles;

    private UserDto user;
    private UUID storeIdEmployee;
    private UUID storeIdManager;

//    public JwtDto(String token, UUID id, String username, String email, List<String> roles) {
//        this.token = token;
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.roles = roles;
//    }

    public JwtDto(String token, UserDto user, UUID storeIdEmployee, UUID storeIdManager, List<String> roles) {
        this.token = token;
        this.user = user;
        this.storeIdEmployee = storeIdEmployee;
        this.storeIdManager = storeIdManager;
        this.roles = roles;
    }

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public List<String> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<String> roles) {
//        this.roles = roles;
//    }
}
