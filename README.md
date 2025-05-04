# Scalable App Task 5: Hotel Booking Workflow with RabbitMQ and OpenFeign

## Overview

This repository contains the implementation of **Task 5** for the *Architecture of Massively Scalable Applications* course at the **German University in Cairo**, Department of Computer Science, under **Assoc. Prof. Mervat Abu El-Kheir**, Spring 2025.

The task focuses on implementing communication techniques in an online hotel booking workflow, integrating **RabbitMQ** for asynchronous messaging and **OpenFeign** for synchronous service communication using **Spring Boot**.

---

## Project Description

### Services

- **Availability Service**: Checks room availability for a given room type and number of nights.
- **Notification Service**: Receives booking notifications via RabbitMQ.
- **Booking Service**: Manages bookings, checks availability, and sends notifications.

### Communication Patterns

- **Blocking**: Availability Service is contacted via **OpenFeign**.
- **Non-Blocking**: Notification Service receives messages via **RabbitMQ**.

---

## Features

### Availability Service Integration (OpenFeign)

- **Feign Client**: `AvailabilityClient` is implemented as a `@FeignClient("availability-service")`.
- **Endpoint**: `/check/{roomType}/{nights}` returns a boolean (e.g., `true` if available).
- **Example**: `GET http://localhost:8091/availability/check/double/2`

---

### Notification Service (RabbitMQ Consumer)

- **Listener Method**: `onNewBooking()` listens to `booking_queue_YourName_52_1234`.
- **Log Example**:
```

Received new booking from 52-1234: <bookingId> YourName\_52\_1234

```

- **RabbitMQ Config**:
- Queue: `booking_queue_YourName_52_1234`
- Exchange: `YourName_52_1234`
- Routing Key: `booking_routing_YourName_52_1234`

---

### Booking Service (RabbitMQ Producer + Logic)

- **Feign Client**: Calls availability check service.
- **Booking Logic**:
- If available: generate `UUID`, send message via RabbitMQ.
- If not: throw `ResponseStatusException` (400 BAD_REQUEST).
- **Log Example**:
```

Sent from 52-1234: <bookingId> YourName\_52\_1234

````

---

## Controllers

- **BookingController**: Handles booking requests via `BookingService.createBooking()`.

---

## Docker Setup

- Run RabbitMQ using:
```bash
docker-compose up -d
````

* RabbitMQ UI: [http://localhost:15672](http://localhost:15672)

  * Username: `guest`
  * Password: `guest`

---

## Setup and Usage

### 1. Configure Environment

Edit `application.yml`:

```yaml
name: YourName_SecondName
id: 52-1234
```

---

### 2. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

---

### 3. Test Booking Flow

* Send booking request from Booking Service.
* Workflow:

  1. Calls Availability Service at `localhost:8091`.
  2. Sends booking message to RabbitMQ.
  3. Notification Service consumes the message.

---

## Skills Demonstrated

* Proficiency in **RabbitMQ** for messaging in distributed systems.
* Expertise in **OpenFeign** for REST client abstraction.
* Solid **Spring Boot** microservice architecture.
* Experience with **Docker** for isolated service deployment.
* Implementation of **blocking and non-blocking** communication.
* Strong error handling and service resilience.

---

## Why Hire Me?

This task showcases my ability to design robust, scalable service architectures. I effectively integrated synchronous (OpenFeign) and asynchronous (RabbitMQ) communication patterns and ensured clean service separation and error handling—ideal for microservices-based enterprise systems.

---

## Submission Details

* Environment: `YourName_52_1234`
