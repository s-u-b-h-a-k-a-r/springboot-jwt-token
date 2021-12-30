package com.subhakar.jwt.controller;

import static com.subhakar.jwt.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import com.subhakar.jwt.controller.dto.UserDTO;
import com.subhakar.jwt.mapper.UserMapper;
import com.subhakar.jwt.entity.User;
import com.subhakar.jwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    public UserDTO getUser(@PathVariable String username) {
        return userMapper.toUserDTO(userService.validateAndGetUserByUsername(username));
    }

    @DeleteMapping("/{username}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    public UserDTO deleteUser(@PathVariable String username) {
        User user = userService.validateAndGetUserByUsername(username);
        userService.deleteUser(user);
        return userMapper.toUserDTO(user);
    }
}
