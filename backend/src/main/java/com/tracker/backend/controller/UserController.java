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

    @GetMapping("/get/all/users")
    public List<UserDto> getAllUsers() {
        return getUserFacade().getAllUsers();
    }

    @GetMapping("/get/user/by/username/email")
    public UserDto getUserByUsernameOrEmail(@RequestParam final String name) {return getUserFacade().getUserByUsernameOrEmail(name);}

    @PostMapping("/create/user")
    public UserDto createUser(@RequestBody final UserDto userDto) {
        return getUserFacade().createUser(userDto);
    }

    @DeleteMapping("delete/user/by/{id}")
    public void deleteUserById(@PathVariable final Long id) {getUserFacade().deleteUser(id);}
}