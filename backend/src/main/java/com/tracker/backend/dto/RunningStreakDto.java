package com.tracker.backend.dto;

import lombok.Data;

@Data
public class RunningStreakDto {
    private int currentStreak;
    private int longestStreak;
}