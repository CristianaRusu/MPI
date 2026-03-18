package com.tracker.backend.service;

import com.tracker.backend.dto.UserDto;
import com.tracker.backend.entity.User;
import com.tracker.backend.repository.UserRepository;
import com.tracker.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetAllUsers() {
        User user = new User();
        user.setUsername("mariana_dobrogeanu");
        user.setEmail("mariana@example.com");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("mariana_dobrogeanu", users.get(0).getUsername());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetUserById() {
        userService.getUserById(1L);
        assertDoesNotThrow(() -> userService.getUserById(1L));
    }
}