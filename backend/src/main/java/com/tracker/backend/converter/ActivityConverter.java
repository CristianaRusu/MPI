package com.tracker.backend.converter;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.entity.Activity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActivityConverter {
    public static ActivityDto entityToActivityDto(final Activity activity) {
        if (activity == null) {
            log.error("activity is null");
        }

        return new ActivityDto(
                activity.getId(),
                UserConverter.entityToUserDto(activity.getUser()),
                activity.getStartTime(),
                activity.getEndTime()
        );
    }

    public static Activity DtoToActivityEntity(final ActivityDto activityDto) {
        if (activityDto == null) {
            log.error("activityDto is null");
        }

        final Activity activity = new Activity();
        activity.setId(activityDto.getId());
        activity.setUser(UserConverter.dtoToUserEntity(activityDto.getUserDto()));
        activity.setStartTime(activityDto.getStartTime());
        activity.setEndTime(activityDto.getEndTime());

        return activity;
    }
}
