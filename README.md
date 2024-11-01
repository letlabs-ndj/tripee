# Spring Boot Microservices Application  for Tripee

This project is a Spring Boot-based microservices architecture for our ridesharing application **Tripee**. The application is built using microservices to ensure modularity, scalability, and flexibility in deployment. 
Each service is self-contained with its own business logic and can communicate with other services.

## Table of Contents

- [Overview](#overview)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)

---

## Overview

This application contains the following microservices:

- **Config Server**: Centralized configuration management for all services.
- **Discovery Service**: Service registry and discovery for efficient inter-service communication.
- **Account Service**: Manages user accounts and authentication.
- **Ride Service**: Handles ride management and tracking.
- **Expedition Service**: Manages user expeditions.
- **Reservation Service**: Manages user ride reservation.
- **Chat Service**: Enables real-time messaging between users.
- **Notification Service**: Sends notifications for events and updates.
- **API Gateway**: Entry point for client requests, routing them to respective services.

## Getting Started

These instructions will help you set up and run the application on your local machine for development and testing purposes.

### Prerequisites

- **Java 17** or higher
- **Maven 3.8** or higher
- **Docker** (for running containers)
- **Twilio Account** (for OTP authentication, see below)

### Running the Application

1. **Clone the Repository:**
   ```bash
   git clone  https://github.com/Lameute-Org/tripee.git
   cd spring-boot-backend/service
   ```

2. **Twilio Setup for OTP Authentication**  
   To enable OTP (One-Time Password) authentication, you’ll need a **Twilio Account**. Twilio provides a free trial account with limited features, including the ability to authenticate the phone number registered during the account creation. To set up Twilio for OTP, follow these steps:

   - **Create a Twilio Account** at [Twilio's Website](https://www.twilio.com/).
   - In your Twilio Console, find and copy the following credentials:
      - **ACCOUNT SID**: Your Twilio Account SID
      - **AUTH TOKEN**: Your Twilio Auth Token
   - Once your account is created, create a new verify service. You can follow the steps on [Create verify service](https://help.twilio.com/articles/360033309133-Getting-Started-with-Twilio-Verify-V2) (Configure your otp to 4-digits). Find and copy the following credential:
      - **SERVICE ID**: Your Twilio Service SID for OTP messaging
   - Add all these credentials to the `account-service.yml` file located in the `resources` folder of the `config-server`. Set the properties as follows:

     ```yaml
     twilio:
       account_sid: ${TWILIO_ACCOUNT_SID}
       auth_token: ${TWILIO_AUTH_TOKEN}
       service_id: ${TWILIO_SERVICE_ID}
     ```

   > **Note**: Twilio's free account will limit OTP messages to the registered phone number until upgraded to a paid plan.

   > **Note:** Follow the next steps in the order specified below.

3. **Start Docker Containers:**
   Use `docker-compose` to run the necessary containers (database containers, Kafka, etc.) defined in the root `docker-compose.yml` file:
   ```bash
   docker-compose up -d
   ```

4. **Run Config Server:**
   Navigate to the `config-server` directory and start the service:
   ```bash
   cd config-server
   mvn spring-boot:run
   ```

5. **Run Discovery Service:**
   Navigate to the `discovery-service` directory and start the service:
   ```bash
   cd discovery-service
   mvn spring-boot:run
   ```

6. **Run Business Services:**
   For each of the following services, navigate to the respective directory and execute the command to start the service:

   - **Account Service**:
     ```bash
     cd account-service
     mvn spring-boot:run
     ```

   - **Ride Service**:
     ```bash
     cd ride-service
     mvn spring-boot:run
     ```

   - **Reservation Service**:
     ```bash
     cd reservation-service
     mvn spring-boot:run
     ```

   - **Expedition Service**:
     ```bash
     cd expedition-service
     mvn spring-boot:run
     ```

7. **Run API Gateway:**
   Finally, navigate to the `api-gateway` directory and start the gateway service:
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```

### API Documentation
Each microservice is accessible through a base url and provides a Swagger documentation, accessible on the link provided on the table.

| Microservice             | Base URL                             | Swagger Documentation                                                       |
|--------------------------|--------------------------------------|-----------------------------------------------------------------------------|
| **Config Server**        | `http://localhost:8888`              | -                                                                           |
| **Discovery Service**    | `http://localhost:8761`              | -                                                                           |
| **Account Service**      | `http://localhost:8089/users`        | [Link](http://localhost:8089/doc/account-service/swagger-ui/index.html)     |
| **Ride Service**         | `http://localhost:8089/rides`        | [Link](http://localhost:8089/doc/ride-service/swagger-ui/index.html)        |
| **Expedition Service**   | `http://localhost:8089/expeditions`  | [Link](http://localhost:8089/doc/expedition-service/swagger-ui/index.html)  |
| **Reservation Service**  | `http://localhost:8089/reservations` | [Link](http://localhost:8089/doc/reservation-service/swagger-ui/index.html) |
| **Chat Service**         | `http://localhost:8080/chat`         | [Link](http://localhost:8085/doc/account-service/swagger-ui/index.html)     |
| **Notification Service** | -                                    | -                                                                           |
| **API Gateway**          | `http://localhost:8089`              | -                                                                           |

> **Note:** All external requests should be routed through the **API Gateway** on port **8089**, except for the **Chat Service**, which is accessed directly on its specific port.

