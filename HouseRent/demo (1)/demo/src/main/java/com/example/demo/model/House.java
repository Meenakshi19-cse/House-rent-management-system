package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String address;
    private int rooms;
    private double rent;
    private String locationDescription;
    private String nearbyAttractions;
    private boolean parkingAvailable;
    private boolean nearCity;
    @Column(length = 1000)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    private Owner owner;
}
