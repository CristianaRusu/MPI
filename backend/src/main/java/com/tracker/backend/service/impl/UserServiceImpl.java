package com.tracker.backend.service.impl;

import com.tracker.backend.converter.UserConverter;
import com.tracker.backend.dto.UserDto;
import com.tracker.backend.entity.User;
import com.tracker.backend.repository.UserRepository;
import com.tracker.backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    @Override
    public void getUserById(long l) {

    }

    @Override
    public ResponseEntity<String> changePassword(Long id, String currentPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Userul nu a fost găsit!"));

        if (!user.getPassword().equals(currentPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Parola curentă este incorectă!");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return ResponseEntity.ok("Parola a fost schimbată cu succes!");
    }

    @Override
    public ResponseEntity<String> uploadProfileImage(Long id, MultipartFile image) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Userul nu a fost găsit!"));

        user.setProfileImage(image.getBytes());
        userRepository.save(user);
        return ResponseEntity.ok("Imaginea a fost încărcată cu succes!");
    }
}