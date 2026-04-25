package com.tracker.backend.controller;

import com.tracker.backend.dto.ActivityDto;
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

    @GetMapping("/runs")
    public List<ActivityDto> getFilteredRuns(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Double minDistance,
            @RequestParam(required = false) Double maxDistance,
            @RequestParam(required = false) Double minPace,
            @RequestParam(required = false) Double maxPace
    ) {
        return getActivityFacade().getFilteredRuns(startDate, endDate, minDistance, maxDistance, minPace, maxPace);
    }
}