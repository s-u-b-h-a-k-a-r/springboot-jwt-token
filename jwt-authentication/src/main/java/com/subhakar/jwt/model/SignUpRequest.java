package com.subhakar.jwt.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SignUpRequest {
    @Schema(example = "user3")
    @NotBlank
    private String username;
    @Schema(example = "user3")
    @NotBlank
    private String password;
    @Schema(example = "user3@mycompany.com")
    @NotBlank
    private String email;
    @NotBlank
    private Set<String> roles;
}
