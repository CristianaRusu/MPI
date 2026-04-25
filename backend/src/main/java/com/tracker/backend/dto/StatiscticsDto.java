package com.tracker.backend.dto;

import lombok.Data;

@Data
public class StatiscticsDto {
    private Long totalTime;
    private Double totalDistance;
    private Double mediumPace;
    private int totalActivityCount;
}
