package com.tracker.backend.repository;

import com.tracker.backend.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("""
    SELECT a FROM Activity a
    WHERE (CAST(:startDate AS timestamp) IS NULL OR a.startTime >= CAST(:startDate AS timestamp))
      AND (CAST(:endDate AS timestamp) IS NULL OR a.startTime <= CAST(:endDate AS timestamp))
      AND (:minDistance IS NULL OR a.distanceKm >= :minDistance)
      AND (:maxDistance IS NULL OR a.distanceKm <= :maxDistance)
      AND (:minPace IS NULL OR a.pace >= :minPace)
      AND (:maxPace IS NULL OR a.pace <= :maxPace)
""")
    List<Activity> findWithFilters(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Double minDistance,
            Double maxDistance,
            Double minPace,
            Double maxPace
    );
}
