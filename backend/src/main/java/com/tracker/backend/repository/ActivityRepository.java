package com.tracker.backend.repository;

import com.tracker.backend.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId ORDER BY a.startTime DESC")
    List<Activity> findActivitiesByUserId(@Param("userId") Long userId);
}
