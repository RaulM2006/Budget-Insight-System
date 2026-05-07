# Budget Insight System (BIS) — Iteration 1

## Overview
Iteration 1 delivers the foundational architecture: layered structure, ORM entities, authentication (F2), and CSV import (F1).

## Tech Stack
| Concern | Technology |
|---|---|
| Language | Java 17 |
| Build | Gradle |
| Framework | Spring Boot 3.2 |
| ORM | Hibernate (via Spring Data JPA) |
| Database | PostgreSQL |
| UI | JavaFX 21 |
| Password Hashing | BCrypt (Spring Security Crypto) |
| CSV Parsing | OpenCSV |
| Testing | JUnit 5 + Mockito + AssertJ |

## Architecture
```
src/main/java/com/bis/
├── BisApplication.java              ← Entry point (Spring Boot + JavaFX bootstrap)
├── DashboardWindowContext.java      ← Bridge: Spring bean → JavaFX window
│
├── data/                            ← DATA LAYER
│   ├── entity/
│   │   ├── User.java                ← JPA entity mapped to "users" table
│   │   └── Expense.java             ← JPA entity mapped to "expenses" table
│   └── repository/
│       ├── UserRepository.java      ← Spring Data JPA repository
│       └── ExpenseRepository.java
│
├── business/                        ← BUSINESS LAYER
│   └── service/
│       ├── UserService.java         ← F2: Authentication logic
│       ├── CsvImportService.java    ← F1: CSV import logic
│       ├── ImportResult.java        ← Value object / DTO
│       ├── AuthenticationException.java
│       └── InvalidFormatException.java
│
└── presentation/                    ← PRESENTATION LAYER
    ├── controller/
    │   ├── AppConfig.java           ← Spring @Configuration (BCrypt bean)
    │   ├── LoginController.java     ← Mediates LoginWindow ↔ UserService
    │   └── ImportController.java    ← Mediates DashboardWindow ↔ CsvImportService
    └── view/
        ├── LoginWindow.java         ← JavaFX login screen
        └── DashboardWindow.java     ← JavaFX main dashboard
```

## Setup

### 1. Database
Create a PostgreSQL database and user:
```sql
CREATE DATABASE bis_db;
CREATE USER bis_user WITH PASSWORD 'bis_password';
GRANT ALL PRIVILEGES ON DATABASE bis_db TO bis_user;
```

### 2. Configure (optional)
Edit `src/main/resources/application.properties` if your DB credentials differ.

### 3. Run
```bash
./gradlew run
```
Hibernate will auto-create the `users` and `expenses` tables on first run.

### 4. Tests
```bash
./gradlew test
```
Tests use Mockito mocks — no database needed.

## CSV Import Format
The system expects a CSV with this exact header:
```
username,password,date,value,category
```
- `date` format: `yyyy-MM-dd`
- `value` must be a positive number
- `category` is optional (can be blank)

A sample file is provided: `sample_import.csv`

## Deliverable (Iteration 1 ✅)
- [x] Layered architecture (Data / Business / Presentation)
- [x] ORM entities: `User`, `Expense` (Hibernate + PostgreSQL)
- [x] F2: Login with BCrypt password verification
- [x] F2: Logout
- [x] F1: CSV import with validation and error handling
- [x] JavaFX UI: LoginWindow + DashboardWindow
- [x] Unit tests: UserService (6 cases) + CsvImportService (8 cases)
