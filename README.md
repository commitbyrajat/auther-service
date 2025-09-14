# Author Management Service

This project is a reactive Spring Boot application for managing authors and their books. It exposes REST APIs for CRUD operations and demonstrates the use of **Spring WebFlux**, **R2DBC**, and **Flyway** for database migrations.

---

## Tech Stack

* **Java 21** – Programming language
* **Spring Boot 3.5.4** – Application framework
* **Spring WebFlux** – Reactive REST API framework
* **R2DBC** – Reactive database connectivity
* **PostgreSQL** – Database
* **Flyway** – Database migrations
* **JUnit 5 & Mockito** – Unit testing
* **Maven** – Build and dependency management

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-org/author-management.git
cd author-management
```

### 2. Configure Database

Ensure PostgreSQL is installed and running.
Create a database for the service, for example:

```sql
CREATE DATABASE author_db;
```

Update connection details in `src/main/resources/application-qa.yml`:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/author_db
    username: your_username
    password: your_password

spring:
  flyway:
    url: jdbc:postgresql://localhost:5432/author_db
    user: your_username
    password: your_password
    locations: classpath:db/migration
```

---

## Running Database Migrations

Flyway runs migrations automatically on application startup.
To run migrations manually:

```bash
mvn flyway:migrate
```

Migration scripts are located in:

```
src/main/resources/db/migration
```

Each script should follow the Flyway naming convention, for example:

```
V1__create_author_table.sql
V2__create_book_table.sql
```

---

## Running the Application

To run the application with the `qa` profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=qa
```

---

## Running Unit Tests

To run all unit tests with the `qa` profile:

```bash
mvn clean test -Dspring.profiles.active=qa
```

Tests use **Mockito** for mocking dependencies and **WebTestClient** for controller testing.

---

## API Endpoints

* `GET /api/authors` → Get all authors
* `GET /api/authors/{id}` → Get author with books
* `POST /api/authors` → Create new author
* `PUT /api/authors/{id}` → Update author
* `DELETE /api/authors/{id}` → Delete an author
* `POST /api/authors/{id}/books` → Add book to author
* `PUT /api/authors/{authorId}/books/{bookId}` → Update book by author
* `DELETE /api/authors/{authorId}/books/{bookId}` → Delete book by author
* `GET /api/authors/{id}/books` → Get all books of an author

---

