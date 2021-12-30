package com.subhakar.jwt.runner;

import com.subhakar.jwt.security.WebSecurityConfig;
import com.subhakar.jwt.service.UserService;
import com.subhakar.jwt.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });
        log.info("Database initialized");
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "admin@mycompany.com", Stream.of(WebSecurityConfig.ADMIN).collect(Collectors.toSet())),
            new User("user", "user", "user@mycompany.com", Stream.of(WebSecurityConfig.USER).collect(Collectors.toSet()))
    );
}
