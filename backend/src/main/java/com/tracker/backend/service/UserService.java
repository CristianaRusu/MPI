package com.tracker.backend.service;

import com.tracker.backend.dto.UserDto;
import com.tracker.backend.entity.User;
import com.tracker.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // RUTA GET: Luam Entity din DB, il facem DTO si il trimitem
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto) // magia care transforma fiecare user in dto
                .collect(Collectors.toList());
    }

    // RUTA POST: Primim un DTO de pe net, il facem Entity ca sa-l salvam in DB, apoi returnam DTO
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        // Nu setam weightKg aici, il protejam

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    // Functie utilitara - "Traducatorul"
    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}