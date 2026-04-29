package com.tracker.backend.service;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.dto.RunningStreakDto;
import com.tracker.backend.dto.StatisticsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityService {
    List<ActivityDto> getAllActivities();
    ActivityDto createActivity(final ActivityDto activityDto);
    ActivityDto getActivityById(final Long id);
    void deleteActivity(final Long id);
    RunningStreakDto calculateRunningStreak(Long userId);
    List<ActivityDto> getFilteredRuns(final Long userId,
                                      final LocalDateTime startDate,
                                      final LocalDateTime endDate,
                                      final Double minDistance,
                                      final Double maxDistance,
                                      final Double minPace,
                                      final Double maxPace);
    Double calculatePace(Double distance, Double duration);
    StatisticsDto getStatistics(Long userId);
}
