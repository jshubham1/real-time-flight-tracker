package com.shubham.flighttracker.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubham.flighttracker.entity.Flight;
import com.shubham.flighttracker.repository.FlightRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FlightDataConsumer {

    private final FlightRepository flightRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FlightDataConsumer(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @KafkaListener(topics = "flight-data-raw", groupId = "flight-processor")
    public void consume(String message) throws JsonProcessingException {
        // The JSON from AviationStack is nested. We need to parse it.
        // This is a simplified example. You'll need to map the complex JSON
        // from the API into your clean Flight entity.
        JsonNode root = objectMapper.readTree(message);
        JsonNode data = root.path("data");

        if (data.isArray()) {
            for (JsonNode flightNode : data) {
                Flight flight = new Flight();
                flight.setFlightIata(flightNode.path("flight").path("iata").asText());
                // ... extract other fields
                flight.setStatus(flightNode.path("flight_status").asText());
                flightRepository.save(flight);
            }
        }
        System.out.println("Processed and saved " + data.size() + " flights.");
    }
}