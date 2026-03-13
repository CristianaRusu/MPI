package com.tracker.backend.controller;

import com.tracker.backend.dto.UserDto;
import com.tracker.backend.facade.UserFacade;
import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserFacade userFacade;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return getUserFacade().getAllUsers();
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return getUserFacade().createUser(userDto);
    }
}