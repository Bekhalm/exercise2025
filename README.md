Small Spring Boot REST API for managing pets, backed by MySQL via Spring Data JPA and secured with HTTP Basic.

## Tech stack

- Spring Boot 3.5.6, Java 25
- Spring Web, Spring Data JPA, Spring Security, Bean Validation
- Thymeleaf (dependency on classpath; no HTML views yet)
- MySQL 9.4 (runtime, via Docker)
- H2 (tests)
- Maven

```bash
docker compose up -d
mvn spring-boot:run
```

## Endpoints

- `GET  /pets` – public
- `POST /pets` – auth required (user `alice`, password `password`)

## Test

```bash
mvn test
```
