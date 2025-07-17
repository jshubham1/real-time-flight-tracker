package com.shubham.flighttracker.repository;

import com.shubham.flighttracker.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, String> {}
