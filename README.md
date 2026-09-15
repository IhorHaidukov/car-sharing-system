# Car Sharing System

Car Sharing System is a backend REST API built with Java and Spring Boot.

The application allows users to register, authenticate using JWT, view available cars, and manage car rentals. The system supports role-based authorization with USER and ADMIN roles.

## Project Status

🚧 In Development

The project is actively being developed as part of my Java Backend portfolio.

## Technologies

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok
- Jakarta Validation
- REST API
- Git & GitHub

## Features

- User registration
- Password encryption with BCrypt
- User authentication with JWT
- Role-based authorization (USER / ADMIN)
- Car CRUD operations
- Rental management
- Rental price calculation
- User-specific rental history
- PostgreSQL database integration
- Global exception handling
- Request validation

### Access Control

- Guests can view cars
- Users can authenticate and manage their rentals
- Admins can manage cars
- Admins can view all users

## API Endpoints

### Authentication
- `POST /auth/login` — User login and JWT generation

### Users
- `POST /api/users` — Register a new user
- `GET /api/users/all` — Get all users (ADMIN)

### Cars
- `GET /api/cars` — Get all cars
- `GET /api/cars/{id}` — Get car by ID
- `POST /api/cars` — Create a car (ADMIN)
- `PUT /api/cars/{id}` — Update a car (ADMIN)
- `DELETE /api/cars/{id}` — Delete a car (ADMIN)

### Rentals
- `POST /api/rentals` — Create a rental
- `GET /api/rentals/my` — Get rentals of the authenticated user

## Roadmap

- Prevent double booking of cars
- Improve rental date and availability validation
- Improve JWT validation and error handling
- Add unit tests with JUnit and Mockito
- Add integration tests
- Add Docker support
- Improve API documentation