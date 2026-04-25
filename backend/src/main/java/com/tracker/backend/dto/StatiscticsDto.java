package com.tracker.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

/*
distanța totală alergată
timpul total de alergare
pace-ul mediu
numărul total de alergări
 */
@Data
public class StatiscticsDto {
    private LocalDateTime totalTime;
    private Double totalDistance;
    private Double mediumPace;
    private int totalActivityCount;
}
