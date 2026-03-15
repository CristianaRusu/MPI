package com.tracker.backend.controller;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.facade.ActivityFacade;
import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    @Resource
    private ActivityFacade activityFacade;

    @GetMapping("/get/all/activities")
    public List<ActivityDto> getAllActivities() {return getActivityFacade().getAllActivities();}

    @GetMapping("/get/activity/by/{id}")
    public ActivityDto getActivityById(@PathVariable final Long id) {
        return getActivityFacade().getActivityById(id);
    }

    @PostMapping("/create/activity")
    public ActivityDto createActivity(@RequestBody final ActivityDto activityDto) {
        return getActivityFacade().createActivity(activityDto);
    }

    @DeleteMapping("delete/activity/by/{id}")
    public void deleteActivityById(@PathVariable final Long id) {
        getActivityFacade().deleteActivity(id);
    }
}
