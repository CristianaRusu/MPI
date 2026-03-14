package com.tracker.backend.service.impl;

import com.tracker.backend.converter.UserConverter;
import com.tracker.backend.dto.UserDto;
import com.tracker.backend.entity.User;
import com.tracker.backend.repository.UserRepository;
import com.tracker.backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserConverter::entityToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(final UserDto userDto) {
        final User user = UserConverter.dtoToUserEntity(userDto);
        final User savedUser = userRepository.save(user);
        return UserConverter.entityToUserDto(savedUser);
    }

    @Override
    public UserDto getUserByUsernameOrEmail(String name) {
        return UserConverter.entityToUserDto(userRepository.findByUsernameOrEmail(name, name));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}