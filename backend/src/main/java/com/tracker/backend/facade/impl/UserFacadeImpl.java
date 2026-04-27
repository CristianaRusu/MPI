package com.tracker.backend.facade.impl;

import com.tracker.backend.dto.UserDto;
import com.tracker.backend.facade.UserFacade;
import com.tracker.backend.service.UserService;
import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Component
@Getter(AccessLevel.PROTECTED)
public class UserFacadeImpl implements UserFacade {
    @Resource private UserService userService;
    @Override
    public List<UserDto> getAllUsers() {
        return getUserService().getAllUsers();
    }

    @Override
    public UserDto createUser(final UserDto userDto) {
        return getUserService().createUser(userDto);
    }

    @Override
    public UserDto getUserByUsernameOrEmail(final String name) {
        return getUserService().getUserByUsernameOrEmail(name);
    }

    @Override
    public void deleteUser(final Long id) { getUserService().deleteUser(id); }

    @Override
    public ResponseEntity<String> changePassword(Long id, String currentPassword, String newPassword) {
        return getUserService().changePassword(id, currentPassword, newPassword);
    }

    @Override
    public ResponseEntity<String> uploadProfileImage(Long id, MultipartFile image) throws IOException {
        return getUserService().uploadProfileImage(id, image);
    }
}
