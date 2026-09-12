# Expense Tracker Backend

A RESTful backend application for managing personal income and expenses, built with Java and Spring Boot. The application provides JWT-based authentication, transaction management, pagination, sorting, validation, exception handling, and Docker containerization.

## 🚀 Features

* User registration and login
* JWT-based authentication and authorization
* Add, update, view, and delete transactions
* Paginated transaction retrieval
* Transactions sorted by date in descending order
* Calculate total income and total expenses
* DTO-based request/response handling
* Request validation
* Global exception handling
* MySQL database integration
* Dockerized Spring Boot application
* Docker Compose setup
* REST API testing using Postman

## 🛠️ Tech Stack

| Technology      | Usage                          |
| --------------- | ------------------------------ |
| Java            | Backend programming            |
| Spring Boot     | REST API development           |
| Spring Security | Authentication & authorization |
| JWT             | Stateless authentication       |
| Spring Data JPA | Database interaction           |
| Hibernate       | ORM                            |
| MySQL           | Relational database            |
| Maven           | Dependency management & build  |
| Docker          | Containerization               |
| Docker Compose  | Multi-container setup          |
| Postman         | API testing                    |

## 🏗️ Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Additional components include:

```text
Security Filter → JWT Validation → Spring Security Context
DTOs            → Request/Response Data Transfer
Exception Handler → Centralized Error Handling
```

## 🔐 Authentication Flow

The application uses JWT for stateless authentication.

```text
User
 │
 ├── Register ──→ User stored in database
 │
 └── Login ─────→ Credentials authenticated
                       │
                       ↓
                  JWT generated
                       │
                       ↓
                  Client receives token
                       │
                       ↓
             Authorization: Bearer <token>
                       │
                       ↓
                JWT Authentication Filter
                       │
              ┌────────┴────────┐
              ↓                 ↓
          Valid Token        Invalid Token
              ↓                 ↓
       Request proceeds       Access denied
```

Protected transaction APIs require a valid JWT.

## 📌 API Endpoints

### Authentication

| Method | Endpoint    | Description                        | Authentication |
| ------ | ----------- | ---------------------------------- | -------------- |
| POST   | `/register` | Register a new user                | ❌              |
| POST   | `/login`    | Authenticate user and generate JWT | ❌              |

### Transactions

| Method | Endpoint             | Description                                  | Authentication |
| ------ | -------------------- | -------------------------------------------- | -------------- |
| POST   | `/transactions`      | Add a transaction                            | ✅              |
| GET    | `/transactions`      | Get transactions with pagination and sorting | ✅              |
| PUT    | `/transactions/{id}` | Update a transaction                         | ✅              |
| DELETE | `/transactions/{id}` | Delete a transaction                         | ✅              |

### Transaction Summary

| Method | Endpoint                | Description       | Authentication |
| ------ | ----------------------- | ----------------- | -------------- |
| GET    | `/transactions/income`  | Get total income  | ✅              |
| GET    | `/transactions/expense` | Get total expense | ✅              |

> Endpoint paths may vary depending on the controller implementation.

## 📄 Pagination & Sorting

Transaction retrieval supports pagination and sorting.

Example:

```text
GET /transactions?page=0&size=10&sort=date,desc
```

Where:

* `page=0` → First page
* `size=10` → 10 transactions per page
* `sort=date,desc` → Sort by date in descending order

Pagination is implemented using Spring Data's `Pageable` abstraction.

## ✅ Validation & Exception Handling

The application uses DTO validation to validate incoming requests before processing them.

Examples include:

* Required fields
* Valid transaction amount
* Valid transaction type
* Valid user input

A centralized exception handling mechanism is used to return appropriate responses when errors occur.

## 🐳 Docker

The application is containerized using Docker.

The project includes:

```text
Dockerfile
docker-compose.yml
```

Docker Compose is used to run the application along with its required database services.

### Build the Application

Generate the Spring Boot JAR:

```bash
mvn clean package
```

### Build Docker Images

```bash
docker compose build
```

### Start Containers

```bash
docker compose up
```

### Run in Background

```bash
docker compose up -d
```

### Stop Containers

```bash
docker compose down
```

The APIs can then be tested using Postman.

## 🗂️ Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── ...
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       ├── security/
│   │       ├── exception/
│   │       └── util/
│   │
│   └── resources/
│       └── application.properties
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🧪 API Testing

The APIs were tested using Postman, including:

* User registration
* User login
* JWT-protected requests
* Adding transactions
* Retrieving paginated transactions
* Sorting transactions by date
* Updating transactions
* Deleting transactions
* Income and expense calculations
* Validation and exception scenarios

## 🔮 Future Enhancements

Potential improvements:

* [ ] Unit and integration testing using JUnit and Mockito
* [ ] Swagger/OpenAPI documentation
* [ ] Refresh token implementation
* [ ] Redis caching
* [ ] Monthly/category-wise expense analytics
* [ ] Budget management
* [ ] CI/CD pipeline
* [ ] Cloud deployment

## 📚 Key Concepts Practiced

This project helped reinforce practical knowledge of:

* REST API development
* Layered backend architecture
* Spring Boot
* Spring Security
* JWT authentication
* Spring Data JPA
* Hibernate
* DTO pattern
* Pagination and sorting
* Input validation
* Global exception handling
* MySQL
* Maven
* Docker
* Docker Compose
* API testing

## 👨‍💻 Author

**Niranjan**

Backend development project built as part of continued learning and practical application of Java and Spring Boot.

