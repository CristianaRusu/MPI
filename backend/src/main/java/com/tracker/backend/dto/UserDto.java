package com.tracker.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime lastLogin;

    public UserDto() {}

    public UserDto(Long id, String username, String email, String password, LocalDateTime createdDate, LocalDateTime lastLogin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }
}