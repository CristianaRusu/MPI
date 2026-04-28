package com.tracker.backend.repository;

import com.tracker.backend.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId ORDER BY a.startTime DESC")
    List<Activity> findActivitiesByUserId(@Param("userId") Long userId);

    @Query(value = """
        SELECT 
            SUM(distance_km),
            SUM(EXTRACT(EPOCH FROM (end_time - start_time))),
            COUNT(*)
        FROM activities
        WHERE user_id = :userId
    """, nativeQuery = true)
    List<Object[]> getStatistics(Long userId);
    @Query("""
    SELECT a FROM Activity a
    WHERE a.user.id = :userId
      AND (CAST(:startDate AS timestamp) IS NULL OR a.startTime >= CAST(:startDate AS timestamp))
      AND (CAST(:endDate AS timestamp) IS NULL OR a.startTime <= CAST(:endDate AS timestamp))
      AND (:minDistance IS NULL OR a.distanceKm >= :minDistance)
      AND (:maxDistance IS NULL OR a.distanceKm <= :maxDistance)
      AND (:minPace IS NULL OR a.pace >= :minPace)
      AND (:maxPace IS NULL OR a.pace <= :maxPace)
""")
    List<Activity> findWithFilters(
            Long userId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Double minDistance,
            Double maxDistance,
            Double minPace,
            Double maxPace
    );
}
