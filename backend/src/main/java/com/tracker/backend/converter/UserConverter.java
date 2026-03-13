package com.tracker.backend.converter;

import com.tracker.backend.dto.UserDto;
import com.tracker.backend.entity.User;

public class UserConverter {
    public static UserDto entityToUserDto(final User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedDate(),
                user.getLastLogin()
        );
    }

    public static User dtoToUserEntity(final UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        final User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCreatedDate(userDto.getCreatedDate());
        user.setLastLogin(userDto.getLastLogin());

        return user;
    }
}
