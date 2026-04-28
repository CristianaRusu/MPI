package com.tracker.backend.service.impl;

import com.tracker.backend.converter.ActivityConverter;
import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.dto.RunningStreakDto;
import com.tracker.backend.dto.StatisticsDto;
import com.tracker.backend.entity.Activity;
import com.tracker.backend.repository.ActivityRepository;
import com.tracker.backend.service.ActivityService;
import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.time.LocalDateTime;
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

        Activity savedActivity = getActivityRepository().save(activity);
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
        Activity activity = getActivityRepository().findById(id)
                .orElseThrow(() -> new RuntimeException("Activitatea nu a fost găsită!"));

        return ActivityConverter.entityToActivityDto(activity);
    }

    @Override
    public void deleteActivity(Long id) {
        getActivityRepository().deleteById(id);
    }

    @Override
    public List<ActivityDto> getFilteredRuns(final Long userId,
                                             final LocalDateTime startDate,
                                             final LocalDateTime endDate,
                                             final Double minDistance,
                                             final Double maxDistance,
                                             final Double minPace,
                                             final Double maxPace) {

        List<Activity> runs = getActivityRepository().findWithFilters(
                userId, startDate, endDate, minDistance, maxDistance, minPace, maxPace
        );

        return runs.stream()
                .map(ActivityConverter::entityToActivityDto)
                .toList();
    }

    @Override
    public StatisticsDto getStatistics(Long userId) {

        List<Object[]> results = activityRepository.getStatistics(userId);

        Object[] result = results.isEmpty() ? new Object[]{0, 0, 0} : results.get(0);

        Double totalDistance = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
        Double totalTime = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
        Long count = result[2] != null ? ((Number) result[2]).longValue() : 0L;

        double pace = 0;
        if (totalDistance > 0) {
            pace = (totalTime / 60.0) / totalDistance;
        }

        StatisticsDto dto = new StatisticsDto();
        dto.setTotalDistance(totalDistance);
        dto.setTotalTime(totalTime.longValue()); // secunde
        dto.setMediumPace(pace);
        dto.setTotalActivityCount(count.intValue());

        return dto;
    }

    @Override
    public RunningStreakDto calculateRunningStreak(Long userId) {

        RunningStreakDto dto = new RunningStreakDto();

        var activities = getActivityRepository()
                .findActivitiesByUserId(userId);

        if (activities.isEmpty()) {
            dto.setCurrentStreak(0);
            dto.setLongestStreak(0);
            return dto;
        }

        // Current streak — pornim de la cea mai recentă zi, mergem înapoi
        List<LocalDate> descDays = activities.stream()
                .map(a -> a.getStartTime().toLocalDate())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        int currentStreak = 1;
        for (int i = 0; i < descDays.size() - 1; i++) {
            if (descDays.get(i).minusDays(1).equals(descDays.get(i + 1))) {
                currentStreak++;
            } else {
                break;
            }
        }

        // Longest streak — parcurgem toate zilele în ordine crescătoare
        List<LocalDate> ascDays = activities.stream()
                .map(a -> a.getStartTime().toLocalDate())
                .distinct()
                .sorted()
                .toList();

        int longestStreak = 1;
        int tempStreak = 1;
        for (int i = 1; i < ascDays.size(); i++) {
            if (ascDays.get(i - 1).plusDays(1).equals(ascDays.get(i))) {
                tempStreak++;
                if (tempStreak > longestStreak) {
                    longestStreak = tempStreak;
                }
            } else {
                tempStreak = 1;
            }
        }

        dto.setCurrentStreak(currentStreak);
        dto.setLongestStreak(longestStreak);
        return dto;
    }

}