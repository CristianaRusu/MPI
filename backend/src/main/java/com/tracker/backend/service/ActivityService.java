package com.tracker.backend.service;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.dto.StatiscticsDto;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> getAllActivities();
    ActivityDto createActivity(final ActivityDto activityDto);
    ActivityDto getActivityById(final Long id);
    void deleteActivity(final Long id);

    Double calculatePace(Double distance, Double duration);
    StatiscticsDto getStatistics(Long userId);
}
