package com.tracker.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // "user" e cuvânt rezervat în baza de date, așa că folosim "users"
@Data // Lombok ne generează automat Getters și Setters în spate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private Double weightKg; // Util pentru a calcula caloriile
}