package com.tracker.backend.facade;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.dto.StatiscticsDto;

import java.util.List;

public interface ActivityFacade {
    List<ActivityDto> getAllActivities();
    ActivityDto createActivity(final ActivityDto activityDto);
    ActivityDto getActivityById(final Long id);
    void deleteActivity(final Long id);

    StatiscticsDto getStatistics(Long userId);
}
