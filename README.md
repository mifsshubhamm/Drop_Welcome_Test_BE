# Parking Lot Management System

## Overview

The Parking Lot Management System is a Spring Boot REST API that allows users to manage parking operations, including parking cars, retrieving slot information, and unparking cars. This application provides a simple interface for interacting with a parking facility.

## Features

- **Park a Car**: Easily park a car by providing its license plate.
- **Retrieve Slot Information**: Get information about a specific parking slot.
- **Unpark a Car**: Remove a car from the parking facility by its license plate.
- **Logging**: Comprehensive logging of operations for monitoring and debugging.

## Technologies Used

- Spring Boot
- Java
- Lombok
- Maven
- Resilience4j (for rate limiting)

## Project Overview

Watch the project overview video [here](https://drive.google.com/file/d/1CYp7omJUwABFNReWDUMho_EBnxqvzvqI/view?usp=sharing).

## API Endpoints

### 1. Park a Car

- **Endpoint**: `POST /api/parking/park`
- **Request Body**:
  ```json
  {
    "licensePlate": "UP78BX9207"
  }
  ```
- **Response**: 
  - `200 OK`: Returns a success message indicating the car has been parked.

### 2. Retrieve Slot Information

- **Endpoint**: `GET /api/parking/slot`
- **Request Parameter**: `slot` (integer)
- **Response**:
  - `200 OK`: Returns information about the specified parking slot.

### 3. Unpark a Car

- **Endpoint**: `DELETE /api/parking/unpark`
- **Request Parameter**: `licensePlate` (string)
- **Response**:
  - `200 OK`: Returns a success message indicating the car has been unparked.
  
  
## API Documentation

You can access the API documentation using Swagger UI at the following link:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation

1. Clone the repository:
   ```bash
   git clone 
   cd parking-lot
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Configuration

You can configure the application using the `application.properties` file. Modify the following properties as needed:

```properties
spring.application.name=parking.lot
parking.lot.size=5
logging.file.name=logs/application.log
logging.level.root=INFO
logging.file.max-size=10MB
logging.file.max-history=30
logging.file.total-size-cap=3GB
resilience4j.ratelimiter.instances.apiRateLimiter.limitForPeriod=5
resilience4j.ratelimiter.instances.apiRateLimiter.limitRefreshPeriod=60s
```

## Logging

Logs are stored in the `logs/application.log` file. Make sure the application has the necessary permissions to write to this directory.

## Error Handling

The application handles various exceptions, providing meaningful error messages to the users. Ensure to check logs for details on any errors encountered during operation.
