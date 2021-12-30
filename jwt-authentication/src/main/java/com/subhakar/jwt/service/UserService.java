package com.subhakar.jwt.service;


import com.subhakar.jwt.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    User saveUser(User user);

    void deleteUser(User user);

    boolean hasUserWithEmail(String email);

    boolean hasUserWithUsername(String username);

    Optional<User> getUserByUsername(String username);

    User validateAndGetUserByUsername(String username);
}
