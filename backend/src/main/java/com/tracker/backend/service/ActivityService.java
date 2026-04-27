package com.tracker.backend.service;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.dto.StatisticsDto;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> getAllActivities();
    ActivityDto createActivity(final ActivityDto activityDto);
    ActivityDto getActivityById(final Long id);
    void deleteActivity(final Long id);

    Double calculatePace(Double distance, Double duration);
    StatisticsDto getStatistics(Long userId);
}
