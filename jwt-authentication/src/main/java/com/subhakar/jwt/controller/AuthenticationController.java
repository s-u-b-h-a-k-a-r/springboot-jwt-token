package com.subhakar.jwt.controller;

import com.subhakar.jwt.security.JWTTokenProvider;
import com.subhakar.jwt.service.UserService;
import com.subhakar.jwt.exception.DuplicatedUserInfoException;
import com.subhakar.jwt.model.LoginRequest;
import com.subhakar.jwt.model.LoginResponse;
import com.subhakar.jwt.model.SignUpRequest;
import com.subhakar.jwt.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        return ResponseEntity.ok(new LoginResponse(jwtTokenProvider.generate(authentication), "Token generated successfully!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Username %s already exists", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Email %s already exists", signUpRequest.getUsername()));
        }
        userService.saveUser(new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getRoles()));
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));
        return ResponseEntity.ok(new LoginResponse(jwtTokenProvider.generate(authentication), "Token generated successfully!"));
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(Principal p) {
        return ResponseEntity.ok("You are accessing data after a valid login. You are : " + p.getName());
    }
}
