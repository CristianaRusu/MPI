package com.tracker.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "waypoints")
@Data
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Legătura cu activitatea (O activitate are multe puncte GPS pe hartă)
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    private Double latitude;
    private Double longitude;
    private LocalDateTime recordedAt;
}