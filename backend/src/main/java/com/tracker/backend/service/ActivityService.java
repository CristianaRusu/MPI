package com.tracker.backend.service;

import com.tracker.backend.dto.ActivityDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityService {
    List<ActivityDto> getAllActivities();
    ActivityDto createActivity(final ActivityDto activityDto);
    ActivityDto getActivityById(final Long id);
    void deleteActivity(final Long id);
    List<ActivityDto> getFilteredRuns(LocalDateTime startDate,
                                      LocalDateTime endDate,
                                      Double minDistance,
                                      Double maxDistance,
                                      Double minPace,
                                      Double maxPace);
    Double calculatePace(Double distance, Double duration);
}
