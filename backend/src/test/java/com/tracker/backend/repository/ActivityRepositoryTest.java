package com.tracker.backend.repository;

import com.tracker.backend.entity.Activity;
import com.tracker.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindActivity() {
        User user = new User();
        user.setUsername("tester");
        user.setEmail("test@test.com");
        user.setPassword("1234");
        User savedUser = userRepository.save(user);

        Activity activity = new Activity();
        activity.setDistanceKm(10.5);
        activity.setUser(savedUser);

        Activity savedActivity = activityRepository.save(activity);

        assertThat(savedActivity.getId()).isNotNull();
        Optional<Activity> found = activityRepository.findById(savedActivity.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getDistanceKm()).isEqualTo(10.5);
    }
}