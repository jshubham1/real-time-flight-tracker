package com.shubham.flighttracker.poller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FlightDataPoller {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${flight.api.url}")
    private String apiUrl;

    @Value("${flight.api.key}")
    private String apiKey;

    public FlightDataPoller(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 60000) // Poll every 60 seconds
    public void pollFlightData() {
        String url = apiUrl + "?access_key=" + apiKey;
        // NOTE: In a real app, you might add params like &flight_status=active
        String response = restTemplate.getForObject(url, String.class);

        System.out.println("Fetched data, publishing to Kafka...");
        kafkaTemplate.send("flight-data-raw", response);
    }
}