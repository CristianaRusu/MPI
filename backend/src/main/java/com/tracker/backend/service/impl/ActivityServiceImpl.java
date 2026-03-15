package com.tracker.backend.service.impl;

import com.tracker.backend.converter.ActivityConverter;
import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.entity.Activity;
import com.tracker.backend.repository.ActivityRepository;
import com.tracker.backend.service.ActivityService;
import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityRepository activityRepository;

    @Override
    public List<ActivityDto> getAllActivities() {
        return getActivityRepository().findAll()
                .stream()
                .map(ActivityConverter::entityToActivityDto)
                .toList();
    }

    @Override
    public ActivityDto createActivity(ActivityDto activityDto) {
        final Activity activity = ActivityConverter.dtoToActivityEntity(activityDto);
        final Activity savedActivity= getActivityRepository().save(activity);
        return ActivityConverter.entityToActivityDto(savedActivity);
    }

    @Override
    public ActivityDto getActivityById(Long id) {
        return ActivityConverter.entityToActivityDto(getActivityRepository().findByActivityId(id));
    }

    @Override
    public void deleteActivity(Long id) {
        getActivityRepository().deleteById(id);
    }
}
