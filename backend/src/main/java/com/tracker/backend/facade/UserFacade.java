package com.tracker.backend.facade;

import com.tracker.backend.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface UserFacade {
    List<UserDto> getAllUsers();
    UserDto createUser(final UserDto userDto);
    UserDto getUserByUsernameOrEmail(final String name);
    void deleteUser(final Long id);

    ResponseEntity<String> changePassword(Long id, String currentPassword, String newPassword);
    ResponseEntity<String> uploadProfileImage(Long id, MultipartFile image) throws IOException;
}
