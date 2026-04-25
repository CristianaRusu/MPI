package com.tracker.backend.service.impl;

import com.tracker.backend.converter.ActivityConverter;
import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.dto.StatiscticsDto;
import com.tracker.backend.entity.Activity;
import com.tracker.backend.repository.ActivityRepository;
import com.tracker.backend.service.ActivityService;
import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Getter(AccessLevel.PROTECTED)
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    @Override
    public List<ActivityDto> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(ActivityConverter::entityToActivityDto)
                .toList();
    }

    @Override
    public ActivityDto createActivity(ActivityDto activityDto) {
        Activity activity = ActivityConverter.dtoToActivityEntity(activityDto);

        long durationMillis = Duration
                .between(activityDto.getStartTime(), activityDto.getEndTime())
                .toMillis();

        Double durationMin = (double) durationMillis / 60000;

        Double calculatedPace = calculatePace(
                activityDto.getDistanceKm(),
                durationMin
        );

        activity.setPace(calculatedPace);

        Activity savedActivity = activityRepository.save(activity);
        return ActivityConverter.entityToActivityDto(savedActivity);
    }

    @Override
    public Double calculatePace(Double distance, Double duration) {
        if (distance == null || distance <= 0 || duration == null || duration <= 0) {
            return 0.0;
        }
        return duration / distance;
    }

   @Override
    public ActivityDto getActivityById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activitatea nu a fost găsită!"));

        return ActivityConverter.entityToActivityDto(activity);
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public StatiscticsDto getStatistics(Long userId) {

        List<Object[]> results = activityRepository.getStatistics(userId);

        Object[] result = results.isEmpty() ? new Object[]{0, 0, 0} : results.get(0);

        Double totalDistance = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
        Double totalTime = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
        Long count = result[2] != null ? ((Number) result[2]).longValue() : 0L;

        double pace = 0;
        if (totalDistance > 0) {
            pace = (totalTime / 60.0) / totalDistance;
        }

        StatiscticsDto dto = new StatiscticsDto();
        dto.setTotalDistance(totalDistance);
        dto.setTotalTime(totalTime.longValue()); // secunde
        dto.setMediumPace(pace);
        dto.setTotalActivityCount(count.intValue());

        return dto;
    }
}