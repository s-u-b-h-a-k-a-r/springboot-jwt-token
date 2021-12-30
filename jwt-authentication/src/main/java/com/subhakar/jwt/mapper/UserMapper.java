package com.subhakar.jwt.mapper;

import com.subhakar.jwt.controller.dto.UserDTO;
import com.subhakar.jwt.entity.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);
}
