package com.tracker.backend.dto;

import com.tracker.backend.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityDto {
    private Long id;
    private UserDto userDto;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdDate;
    private Double distanceKm;


    public ActivityDto(Long id, UserDto userDto, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createdDate, Double distanceKm) {
        this.id = id;
        this.userDto = userDto;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdDate = createdDate;
        this.distanceKm = distanceKm;
    }
}
