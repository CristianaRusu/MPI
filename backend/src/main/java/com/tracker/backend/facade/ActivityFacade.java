package com.tracker.backend.facade;

import com.tracker.backend.dto.ActivityDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityFacade {
    List<ActivityDto> getAllActivities();
    ActivityDto createActivity(final ActivityDto activityDto);
    ActivityDto getActivityById(final Long id);
    void deleteActivity(final Long id);
    List<ActivityDto> getFilteredRuns(final Long userId,
                                      final LocalDateTime startDate,
                                      final LocalDateTime endDate,
                                      final Double minDistance,
                                      final Double maxDistance,
                                      final Double minPace,
                                      final Double maxPace);
}
