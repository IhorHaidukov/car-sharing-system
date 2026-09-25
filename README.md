# Car Sharing System

Car Sharing System is a backend REST API built with Java and Spring Boot.

The application allows users to register, authenticate using JWT, view cars, and manage car rentals. The system supports role-based authorization with USER and ADMIN roles.

## Project Status

✅ Core backend functionality completed

The project includes authentication, role-based authorization, car and rental management, business validation, automated tests, PostgreSQL persistence, Swagger documentation, and Docker support.

## Technologies

- Java 22
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- PostgreSQL
- Docker
- Docker Compose
- JUnit 5
- Mockito
- Swagger / OpenAPI
- Maven
- Lombok
- Jakarta Validation
- REST API
- Git & GitHub

## Features

- User registration
- Password encryption with BCrypt
- JWT authentication
- Role-based authorization with USER and ADMIN roles
- Car CRUD operations
- Rental creation, update, deletion, and return
- Rental price calculation
- Prevention of overlapping car rentals
- User-specific rental history
- Admin access to all rentals and users
- PostgreSQL database integration
- Global exception handling
- Request validation
- Unit testing with JUnit 5 and Mockito
- Swagger / OpenAPI documentation
- Dockerized Spring Boot application
- Dockerized PostgreSQL database
- Persistent PostgreSQL data using Docker volumes

## Access Control

- Guests can register and view cars
- Authenticated users can manage their own rentals
- ADMIN users can create, update, and delete cars
- ADMIN users can view all users
- ADMIN users can view all rentals

## API Endpoints

### Authentication

- `POST /auth/login` — User login and JWT generation

### Users

- `POST /api/users` — Register a new user
- `GET /api/users/{id}` — Get user by ID
- `PUT /api/users/{id}` — Update user
- `DELETE /api/users/{id}` — Delete user
- `GET /api/users/all` — Get all users (ADMIN)

### Cars

- `GET /api/cars/all` — Get all cars
- `GET /api/cars/{id}` — Get car by ID
- `POST /api/cars` — Create a car (ADMIN)
- `PUT /api/cars/{id}` — Update a car (ADMIN)
- `DELETE /api/cars/{id}` — Delete a car (ADMIN)

### Rentals

- `POST /api/rentals` — Create a rental
- `GET /api/rentals/{id}` — Get rental by ID
- `PUT /api/rentals/{id}` — Update rental
- `DELETE /api/rentals/{id}` — Delete rental
- `PATCH /api/rentals/{id}/return` — Return a rented car
- `GET /api/rentals/my` — Get rentals of the authenticated user
- `GET /api/rentals/all` — Get all rentals (ADMIN)

## Rental Business Logic

The rental service includes validation for the main rental scenarios:

- A car cannot be rented for overlapping time periods
- Rental end time must be later than start time
- Rental price is calculated using rental duration and the car's hourly price
- Only the rental owner can manage their rental
- An already returned rental cannot be returned again
- Rental price is recalculated when rental dates are updated

If a car is already rented for the requested period, the API returns:

```text
409 Conflict
```

Example response:

```json
{
  "message": "Car is already rented for this time"
}
```

## Authentication

The application uses JWT authentication.

A user logs in using:

```http
POST /auth/login
```

Example request:

```json
{
  "email": "user@example.com",
  "password": "your_password"
}
```

After successful login, the API returns a JWT token.

For protected endpoints, send the token in the Authorization header:

```text
Authorization: Bearer <token>
```

Passwords are stored using BCrypt hashing.

## Swagger / OpenAPI

Swagger UI is available after starting the application:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

Swagger can be used to test registration, authentication, cars, rentals, and protected endpoints.

## Running with Docker

### Requirements

- Docker Desktop
- Docker Compose

### Environment Variables

Create a `.env` file in the project root:

```env
DB_PASSWORD=your_database_password
JWT_SECRET=your_base64_encoded_secret_key
```

The `.env` file is excluded from Git and should not be committed to the repository.

### Start the Application

Build and start the Spring Boot application and PostgreSQL database:

```bash
docker compose up --build
```

Docker Compose starts:

- Spring Boot backend
- PostgreSQL 16 database

The services will be available at:

- Application: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- PostgreSQL: `localhost:5432`

PostgreSQL data is stored in a Docker volume and persists between container restarts.

### Stop the Application

```bash
docker compose down
```

Do not use `docker compose down -v` if you want to keep PostgreSQL data, because `-v` removes the database volume.

## Testing

The project includes unit tests for rental business logic using JUnit 5 and Mockito.

Covered scenarios include:

- Successful rental creation
- Prevention of overlapping rentals
- Invalid rental dates
- User not found
- Car not found
- Successful car return
- Attempt to return an inactive rental
- Rental ownership validation
- Rental price recalculation

The project also includes a Spring Boot context test using a separate H2 test database.

### Run Tests

Linux / macOS:

```bash
./mvnw test
```

Windows:

```powershell
.\mvnw.cmd test
```

## Project Structure

The project follows a layered Spring Boot architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Additional layers and components include:

```text
DTO
Mapper
Validation
Exception Handling
Spring Security
JWT Authentication
```

## Database

The main application uses PostgreSQL.

Main entities:

- User
- Car
- Rental

Relationships:

- A User can have multiple Rentals
- A Car can have multiple Rentals over different time periods
- Each Rental belongs to one User and one Car

## Security

The application uses Spring Security with JWT authentication.

Main security rules:

- Registration is public
- Login is public
- Car viewing is public
- Rental operations require authentication
- Car creation, update, and deletion require ADMIN authority
- Viewing all users requires ADMIN authority
- Viewing all rentals requires ADMIN authority

## Author

Ihor Haidukov

Junior Java Backend Developer

GitHub: https://github.com/IhorHaidukov