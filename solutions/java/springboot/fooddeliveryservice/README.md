# Food Delivery Service – Spring Boot Edition

This module reimagines the `fooddeliveryservice` LLD as a Spring Boot backend. It models customers, restaurants, menu items, orders, delivery partners, payments, and deliveries with an H2-backed schema ready for REST APIs.

## Domain Schema
```mermaid
erDiagram
    CUSTOMER ||--o{ ORDER : places
    RESTAURANT ||--o{ MENU_ITEM : offers
    RESTAURANT ||--o{ ORDER : receives
    MENU_ITEM ||--o{ ORDER_ITEM : ordered_as
    ORDER ||--o{ ORDER_ITEM : contains
    ORDER ||--|| PAYMENT : settles
    ORDER ||--|| DELIVERY : ships
    DELIVERY_PARTNER ||--o{ DELIVERY : fulfills

    CUSTOMER {
        bigint id PK
        varchar name
        varchar phone
        varchar email
        varchar address
    }

    RESTAURANT {
        bigint id PK
        varchar name
        varchar address
        enum status
    }

    MENU_ITEM {
        bigint id PK
        bigint restaurantId FK
        varchar name
        varchar description
        decimal price
        boolean available
    }

    ORDER {
        bigint id PK
        bigint customerId FK
        bigint restaurantId FK
        enum status
        decimal totalAmount
        timestamp placedAt
        timestamp completedAt
    }

    ORDER_ITEM {
        bigint id PK
        bigint orderId FK
        bigint menuItemId FK
        int quantity
        decimal price
    }

    PAYMENT {
        bigint id PK
        bigint orderId FK
        decimal amount
        enum method
        enum status
        timestamp paidAt
    }

    DELIVERY {
        bigint id PK
        bigint orderId FK
        bigint partnerId FK
        enum status
        timestamp assignedAt
        timestamp pickedAt
        timestamp deliveredAt
    }

    DELIVERY_PARTNER {
        bigint id PK
        varchar name
        varchar phone
        enum status
    }
```

## What’s Planned
- Spring Boot 3 + JPA + H2 for an in-memory backend.
- REST endpoints to browse restaurants/menus, place orders, pay, assign delivery partners, and track delivery status.
- DTO records, validation, and MapStruct mapping.
- Seed data and HTTP scratch file for quick manual testing.

## Run & Explore (once implemented)
1. `cd solutions/java/springboot/fooddeliveryservice`
2. `mvn spring-boot:run`
3. Use `requests.http` to exercise the APIs or open `http://localhost:8080/h2-console` (JDBC `jdbc:h2:mem:fooddb`) to inspect data.
