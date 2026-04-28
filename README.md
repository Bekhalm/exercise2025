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
