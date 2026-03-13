package com.tracker.backend.facade;

import com.tracker.backend.dto.UserDto;

import java.util.List;

public interface UserFacade {
    List<UserDto> getAllUsers();
    UserDto createUser(final UserDto userDto);
    UserDto getUserByUsernameOrEmail(final String name);
    void deleteUser(final Long id);
}
