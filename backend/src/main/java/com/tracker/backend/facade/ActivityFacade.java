package com.tracker.backend.facade;

import com.tracker.backend.dto.ActivityDto;

import java.util.List;

public interface ActivityFacade {
    List<ActivityDto> getAllActivities();
    ActivityDto createActivity(final ActivityDto activityDto);
    ActivityDto getActivityById(final Long id);
    void deleteActivity(final Long id);
}
