package com.tracker.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class StatiscticsDto {
    private LocalDateTime totalTime;
    private Double totalDistance;
    private Double mediumPace;
    private int totalActivityCount;
}
