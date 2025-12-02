# ATM – Spring Boot Edition

This module modernizes the `atm` LLD into a Spring Boot REST API that models cards, accounts, cash inventory, ATM sessions, and transactions. It runs on H2 in-memory storage for quick end-to-end simulation.

## Domain Schema
```mermaid
erDiagram
    ATM ||--o{ ATM_SESSION : hosts
    ATM ||--o{ TRANSACTION : logs
    CARD ||--o{ ATM_SESSION : fetch
    ACCOUNT ||--o{ CARD : owns
    ACCOUNT ||--o{ TRANSACTION : affects
    ATM_SESSION ||--o{ TRANSACTION : records

    ATM {
        bigint id PK
        varchar code
        varchar location
        decimal cash_available
        enum status
    }
    ACCOUNT {
        bigint id PK
        varchar number
        varchar holder_name
        decimal balance
        enum status
    }
    CARD {
        bigint id PK
        varchar card_number
        varchar pin_hash
        boolean active
        bigint account_id FK
    }
    ATM_SESSION {
        bigint id PK
        uuid token
        bigint atm_id FK
        bigint card_id FK
        timestamp started_at
        timestamp ended_at
        enum state
    }
    TRANSACTION {
        bigint id PK
        enum type
        enum status
        decimal amount
        timestamp created_at
        bigint account_id FK
        bigint atm_id FK
        bigint session_id FK
        varchar details
    }
```

## What’s Included
- Spring Boot 3, JPA, H2 for persisting accounts, cards, ATMs, sessions, and transactions.
- REST endpoints for session-based auth (card + PIN), balance inquiry, withdrawal, deposit, and session end.
- MapStruct DTO mapping, validation on inputs, and sample data for quick experiments.
- HTTP request samples (`requests.http`) and a Spring Boot test covering happy-path flows (auth → withdraw/deposit → end session).

## Run & Explore
1. `cd solutions/java/springboot/atm`
2. `mvn spring-boot:run`
3. Use `requests.http` to drive the flows or open `http://localhost:8080/h2-console` (JDBC `jdbc:h2:mem:atmdb`) to inspect state.

## API Preview
- `POST /api/atm/sessions` – authenticate card/PIN, start a session.
- `POST /api/atm/sessions/{sessionId}/balance` – check balance.
- `POST /api/atm/sessions/{sessionId}/withdraw` – withdraw cash (checks account + ATM cash).
- `POST /api/atm/sessions/{sessionId}/deposit` – deposit cash.
- `POST /api/atm/sessions/{sessionId}/end` – close the session.

All requests use JSON; DTO contracts live under `dto/`.

## Testing
`mvn test` runs the Spring Boot service test to verify a full session (auth → withdraw → deposit → end) against the in-memory database.
