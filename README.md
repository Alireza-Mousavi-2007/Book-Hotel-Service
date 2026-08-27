# BookingHotelSystem

A hotel room booking backend built with **Spring Boot**, featuring **JWT**-based authentication and a Role/Authority access control model. Users can browse rooms and create bookings; admins manage users, rooms, and all bookings.

> 🤖 **Note :** This README was drafted with the help of AI (Claude), based on a full review of the project's source code.

---

## ✨ Features

- Stateless authentication with JWT (login + signup)
- Roles and Authorities are decoupled: each `Role` (e.g. `ADMIN`, `USER`) holds a set of `Authority` entries (e.g. `CREATE`, `READ`, `UPDATE`, `DELETE`, `CANCEL`)
- Method-level access control via `@PreAuthorize` on every endpoint, including ownership checks (e.g. a user can only cancel or view their own bookings)
- Room management: add, update status, delete, browse by room number
- Booking system with:
  - Automatic booking code generation
  - Overlap detection (prevents double-booking a room for conflicting date ranges)
  - Ownership-based access (users can only cancel/view their own bookings; admins can view all)
- Auto-generated API documentation via Swagger / OpenAPI
- Automatic seeding of base roles, authorities, sample users, and sample rooms on first run

## 🛠️ Tech Stack

| Layer | Tool |
|---|---|
| Language / Framework | Java, Spring Boot |
| Security | Spring Security, java-jwt (Auth0) |
| Database | MySQL, Spring Data JPA / Hibernate |
| API Docs | springdoc-openapi (Swagger UI) |
| Build Tool | Maven (Maven Wrapper) |

## 🏗️ Project Structure

```
src/main/java/org/bookhotel/bookinghotelsystem
├── controller     # REST endpoints (Authentication, User, Room, Booking)
├── dto            # Request/response DTOs
├── entity         # JPA entities (User, Role, Authority, Room, Booking)
├── enums          # RoomStatus and BookingStatus enums
├── exception      # Custom exceptions and a global exception handler
├── repository     # Spring Data JPA repositories
├── security       # Security configuration and JWT (filter, token service)
├── service        # Service layer (interfaces + implementations)
└── swagger        # OpenAPI / Swagger configuration
```

---

## 🚀 Getting Started

### Prerequisites

- JDK (a version compatible with the Spring Boot version used in the project)
- A running MySQL instance (local or remote)
- No separate Maven install needed — the project includes the Maven Wrapper (`mvnw` / `mvnw.cmd`)

### 1. Clone the repository

```bash
git clone <repository-url>
cd BookingHotelSystem
```

### 2. Configure the properties files

For security reasons, the real `application.properties` and `application-{profile}.properties` files are **not** committed to the repository — only `.example` versions are. You need to create them yourself before running the project.

#### a) Main `application.properties`

Path: `src/main/resources/application.properties.example`

Its content looks like this:

```properties
spring.application.name=BookingHotelSystem
spring.profiles.active=yourProfileName
```

1. Make a copy of this file and remove the `.example` suffix, so it's named:
```
application.properties
```
2. Replace `yourProfileName` in `spring.profiles.active` with any profile name you like (e.g. `dev`, `local`). This name just needs to match the file you create in the next step.

#### b) Profile-specific file — `application-{profile}.properties`

Path: `src/main/resources/application-yourProfileName.properties.example`

Its content looks like this:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/booking_hotel_db
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG

security.jwt=a random secretKey
```

1. Make a copy of this file.
2. Rename it to `application-{profile}.properties` (no `.example`), using **the exact same profile name** you chose in step (a). For example, if you set `spring.profiles.active=dev`, this file must be named `application-dev.properties`.
3. Fill in your real values:
   - `spring.datasource.url` — point it to your local MySQL database (create the database first, e.g. `booking_hotel_db`)
   - `spring.datasource.username` / `password` — your MySQL credentials
   - `security.jwt` — a long, random, secret string used to sign JWTs

> ⚠️ **Security note:** Never use a guessable phrase for `security.jwt` (generate one with, for example, `openssl rand -base64 32`). Never commit either properties file — make sure both are covered by `.gitignore`.
>
> The `logging.level.*` lines are optional and only useful for debugging security/web issues; you can remove them for normal use.

### 3. Run the project

Using the Maven Wrapper:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

By default the app starts on `http://localhost:8080`.

### 4. First run — automatic seeding

On first startup, if the sample admin user doesn't already exist, the following are created automatically:
- Base authorities: `CREATE`, `READ`, `UPDATE`, `DELETE`, `CANCEL`
- Roles `ADMIN` (all authorities) and `USER` (`CREATE`, `READ`, `CANCEL`)
- Two sample users (one admin, one regular user)
- A few sample rooms

> This seeding logic lives in the security `Config` class. If you want to change the sample credentials, edit that code before the first run.

---

## 📖 API Documentation (Swagger)

Once the app is running, full interactive API docs are available at:

```
http://localhost:8080/swagger-ui.html
```

## 🔐 Authentication

1. Call `POST /api/auth/login` or `POST /api/auth/signup` to sign in / sign up. The response returns a JWT.
2. Include the token in the `Authorization` header of subsequent requests:

```
Authorization: Bearer <TOKEN>
```

The token is valid for 24 hours and carries an `authorities` claim (the user's list of permissions), which is used to evaluate `@PreAuthorize` on each endpoint. New signups are always assigned the `USER` role — role selection is not exposed to the client.

### Access levels summary

| Endpoint | Access |
|---|---|
| `POST /api/auth/login` | Public |
| `POST /api/auth/signup` | Public — role is always `USER` |
| `GET /api/users/admin` | ADMIN only — list all users |
| `GET /api/users/{username}` | ADMIN, or the user themself |
| `POST /api/users/admin` | ADMIN only — create user with any role |
| `PUT /api/users/{username}` | Self-service — user updates only their own username, email, and password |
| `PUT /api/users/admin/{username}` | ADMIN only — full update of any user, including role |
| `GET /api/rooms/{roomNumber}` | Requires `READ` authority |
| `POST /api/rooms/admin` | ADMIN only — add a room |
| `PUT /api/rooms/admin/{roomNumber}` | ADMIN only — update room status |
| `DELETE /api/rooms/admin/{roomNumber}` | ADMIN only |
| `POST /api/bookings` | Requires `CREATE` authority |
| `GET /api/bookings/{bookingCode}` | ADMIN, or the booking's owner |
| `PATCH /api/bookings/{bookingCode}/cancel` | Requires `CANCEL` authority, and ownership of the booking |
| `GET /api/bookings/admin` | ADMIN only — list all bookings |

---

## 🤝 Contributing

Issues and pull requests are welcome. Please open an issue first to discuss any significant change before submitting a PR.

## 📄 License

<!-- Add your preferred license here, e.g. MIT -->
