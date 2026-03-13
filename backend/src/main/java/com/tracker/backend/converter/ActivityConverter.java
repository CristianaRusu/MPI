package com.tracker.backend.converter;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.entity.Activity;

public class ActivityConverter {
    public static ActivityDto entityToActivityDto(final Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null");
        }

        return new ActivityDto(
                activity.getId(),
                UserConverter.entityToUserDto(activity.getUser()),
                activity.getStartTime(),
                activity.getEndTime(),
                activity.getCreatedDate(),
                activity.getDistanceKm()
        );
    }

    public static Activity DtoToActivityEntity(final ActivityDto activityDto) {
        if (activityDto == null) {
            throw new IllegalArgumentException("Activity cannot be null");
        }

        final Activity activity = new Activity();
        activity.setId(activityDto.getId());
        activity.setUser(UserConverter.dtoToUserEntity(activityDto.getUserDto()));
        activity.setStartTime(activityDto.getStartTime());
        activity.setEndTime(activityDto.getEndTime());
        activity.setCreatedDate(activityDto.getCreatedDate());
        activity.setDistanceKm(activityDto.getDistanceKm());

        return activity;
    }
}
