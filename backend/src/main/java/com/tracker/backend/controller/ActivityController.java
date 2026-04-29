package com.tracker.backend.controller;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.dto.RunningStreakDto;
import com.tracker.backend.dto.StatisticsDto;
import com.tracker.backend.facade.ActivityFacade;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter(AccessLevel.PROTECTED)
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/activities")
public class ActivityController {
    @Resource
    private ActivityFacade activityFacade;

    @GetMapping("/get/all/activities")
    public List<ActivityDto> getAllActivities() {
        return getActivityFacade().getAllActivities();
    }

    @GetMapping("/get/activity/by/{id}")
    public ActivityDto getActivityById(@PathVariable final Long id) {
        return getActivityFacade().getActivityById(id);
    }

    @PostMapping("/create/activity")
    public ActivityDto createActivity(@Valid @RequestBody final ActivityDto activityDto) {
        return getActivityFacade().createActivity(activityDto);
    }

    @DeleteMapping("delete/activity/by/{id}")
    public void deleteActivityById(@PathVariable final Long id) {
        getActivityFacade().deleteActivity(id);
    }

    @GetMapping("/streak/{userId}")
    public RunningStreakDto getRunningStreak(@PathVariable Long userId) {
        return getActivityFacade().calculateRunningStreak(userId);
    }

    @GetMapping("/statistics/{userId}")
    public StatisticsDto getStatistics(@PathVariable Long userId) {
        return getActivityFacade().getStatistics(userId);
    }

    @GetMapping("/runs/{userId}")
    public List<ActivityDto> getFilteredRuns(
            @PathVariable final Long userId,
            @RequestParam(required = false) final LocalDateTime startDate,
            @RequestParam(required = false) final LocalDateTime endDate,
            @RequestParam(required = false) final Double minDistance,
            @RequestParam(required = false) final Double maxDistance,
            @RequestParam(required = false) final Double minPace,
            @RequestParam(required = false) final Double maxPace
    ) {
        return getActivityFacade().getFilteredRuns(userId, startDate, endDate, minDistance, maxDistance, minPace, maxPace);
    }
}