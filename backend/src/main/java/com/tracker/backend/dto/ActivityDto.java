package com.tracker.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Data
public class ActivityDto {
    private Long id;
    private UserDto userDto;

    @PastOrPresent(message = "Data inceperii nu poate fi in viitor")
    private LocalDateTime startTime;

    @PastOrPresent(message = "Data incheierii nu poate fi in viitor")
    private LocalDateTime endTime;

    private LocalDateTime createdDate;

    @Positive(message = "Distanta trebuie sa fie mai mare decat 0")
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