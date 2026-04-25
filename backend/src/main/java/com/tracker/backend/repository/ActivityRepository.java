package com.tracker.backend.repository;

import com.tracker.backend.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query(value = """
        SELECT 
            SUM(distance_km),
            SUM(EXTRACT(EPOCH FROM (end_time - start_time))),
            COUNT(*)
        FROM activities
        WHERE user_id = :userId
    """, nativeQuery = true)
    List<Object[]> getStatistics(Long userId);
}
