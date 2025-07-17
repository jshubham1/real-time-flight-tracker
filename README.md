# Real-Time Flight Tracker System

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/YOUR_USERNAME/real-time-flight-tracker)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A portfolio project demonstrating a scalable, event-driven flight tracking application. The system ingests live flight data, processes it through a Kafka pipeline, stores it in a database, and provides a foundation for real-time user alerts and data visualization.

## Key Features

-   **Microservices Architecture:** Independent services for data ingestion, processing, and user-facing APIs.
-   **Event-Driven with Apache Kafka:** Decoupled services communicating asynchronously for high scalability and resilience.
-   **Real-Time Data Ingestion:** A scheduler regularly polls a public aviation API for the latest flight data.
-   **Scalable Data Processing:** A dedicated consumer service processes and cleans raw data before persisting it.
-   **Infrastructure as Code (IaC):** The entire cloud infrastructure (services, database, Kafka) is defined using Azure Bicep for repeatable, automated deployments.
-   **CI/CD Automation:** A complete CI/CD pipeline built in Azure DevOps automates building, testing, and deploying the services.

## Tech Stack & Architecture

### Technology
-   **Backend:** Java 17, Spring Boot 3, Spring Web, Spring Data JPA
-   **Messaging:** Apache Kafka
-   **Database:** PostgreSQL
-   **DevOps:** Docker, Docker Compose, Maven
-   **Cloud:** Microsoft Azure (Azure App Service, Azure Database for PostgreSQL, Azure Event Hubs for Kafka)
-   **Automation:** Azure DevOps Pipelines, Azure Bicep

### Architecture Diagram

```mermaid
graph TD
    subgraph "External"
        A[Public Flight API]
    end

    subgraph "Our System"
        B(Ingestion Service)
        C{{Kafka Topic: flight-data-raw}}
        D(Processing Service)
        E[(PostgreSQL DB)]
        
        I(Backend API Service)
        J[Frontend UI]

        subgraph "Future: Alerting"
          F{{Kafka Topic: flight-alerts}}
          G(Alerting Service)
          H((User Notifications))
        end
    end

    A --> B
    B -- Publishes --> C
    D -- Subscribes --> C
    D -- Writes Clean Data --> E
    D -- Publishes Events --> F
    G -- Subscribes --> F
    G --> H
    E -- Reads Data --> I
    I -- Serves Data --> J