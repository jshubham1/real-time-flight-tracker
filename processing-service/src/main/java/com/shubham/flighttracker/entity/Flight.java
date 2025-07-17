package com.shubham.flighttracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Flight {
    @Id
    private String flightIata;
    private String departureAirport;
    private String arrivalAirport;
    private String status;
    // Add other fields like latitude, longitude, etc.
}