# Monorepo Project

This repository is a multi-module project containing the following modules:

- **core**: Contains the core business logic and shared components.
- **rest**: Provides a REST API built with Spring Boot and WebFlux.
- **job**: Contains a Spring Boot job that uses the core module.

## Overview

The project is built using Maven and leverages Spring Boot, Spring Data R2DBC, and Flyway for managing database schema and migrations.

## Modules

- **core**: 
  - Contains entities, repositories, services, and configuration classes.
  - Uses reactive programming with Reactor.
- **rest**:
  - Exposes REST endpoints for CRUD operations on authors and books.
  - Contains integration and unit tests.
- **job**:
  - A standalone module that depends on the core module.
  - Contains a simple Spring Boot application entry point.

## Getting Started

### Prerequisites

- Java 21
- Maven 3.x
- A running instance of PostgreSQL for production or H2 for testing

### Building the Project

Run the following command from the root directory:

```bash
mvn clean install
```

### Running the Application

To run the REST API module:

```bash
mvn -pl rest spring-boot:run
```

To run the Job module:

```bash
mvn -pl job spring-boot:run
```

## Testing

To execute unit and integration test, cd into rest module and execute:

```bash
mvn -Dspring-boot.run.profiles=qa clean test
```

### Testing Frameworks

This project utilizes:
- **JUnit 5** for unit testing.
- **Mockito** for mocking dependencies.
- **Reactor Test** for testing reactive streams.

---