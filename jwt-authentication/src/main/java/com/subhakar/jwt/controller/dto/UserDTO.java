package com.subhakar.jwt.controller.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
