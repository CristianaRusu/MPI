package com.tracker.backend.converter;

import com.tracker.backend.dto.UserDto;
import com.tracker.backend.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserConverter {
    public static UserDto entityToUserDto(final User user) {
        if (user == null) {
            log.error("user is null");
        }

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public static User dtoToUserEntity(final UserDto userDto) {
        if (userDto == null) {
            log.error("userDto is null");
        }

        final User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        return user;
    }
}
