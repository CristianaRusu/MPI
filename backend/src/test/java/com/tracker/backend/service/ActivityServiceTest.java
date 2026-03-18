package com.tracker.backend.service;

import com.tracker.backend.dto.ActivityDto;
import com.tracker.backend.entity.Activity;
import com.tracker.backend.entity.User;
import com.tracker.backend.repository.ActivityRepository;
import com.tracker.backend.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Test
    void testGetAllActivities() {
        User user = new User();
        user.setUsername("mariana_test");

        Activity activity = new Activity();
        activity.setDistanceKm(5.0);
        activity.setUser(user);

        when(activityRepository.findAll()).thenReturn(Collections.singletonList(activity));

        List<ActivityDto> result = activityService.getAllActivities();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5.0, result.get(0).getDistanceKm());
    }
}