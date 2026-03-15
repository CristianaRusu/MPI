package com.tracker.backend.facade.impl;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.facade.ActivityFacade;
import com.tracker.backend.service.ActivityService;
import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
@Component
public class ActivityFacadeImpl implements ActivityFacade {

    @Resource
    private ActivityService activityService;
    @Override
    public List<ActivityDto> getAllActivities() {
        return getActivityService().getAllActivities();
    }

    @Override
    public ActivityDto createActivity(ActivityDto activityDto) {
        return getActivityService().createActivity(activityDto);
    }

    @Override
    public ActivityDto getActivityById(Long id) {
        return getActivityService().getActivityById(id);
    }

    @Override
    public void deleteActivity(Long id) {
        getActivityService().deleteActivity(id);
    }
}
