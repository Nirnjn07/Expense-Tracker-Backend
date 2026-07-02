Expense Tracker Backend

Tech Stack
- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Maven

Features
- User Registration
- User Login
- JWT Authentication
- Add / Update / Delete Transactions
- Pagination & Sorting
- Income & Expense Summary
- Input Validation
- Global Exception Handling

Database Schema

### Users Table
| Column | Type | Constraints |
| :--- | :--- | :--- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| `username` | VARCHAR | UNIQUE, NOT NULL |
| `password` | VARCHAR | NOT NULL (BCrypt Hashed) |

### Transactions Table
| Column | Type | Constraints |
| :--- | :--- | :--- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| `type` | VARCHAR | NOT NULL (Income/Expense) |
| `amount` | DECIMAL | NOT NULL |
| `transaction_date`| DATE | NOT NULL |
| `user_id` | BIGINT | FOREIGN KEY (users.id) |

## 🚀 API Endpoints

### Authentication
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| POST | `/api/auth/register` | Register a new user | ❌ |
| POST | `/api/auth/login` | Authenticate user & return JWT | ❌ |

### Transactions
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| POST | `/api/addTransactions` | Add a new transaction | ✅ (JWT) |
| GET | `/api/getTransactions` | Get user's transactions (supports `?page=0&size=10`) | ✅ (JWT) |
| GET | '/api/getTotalExpense'      | Get user's total spending | JWT
| PUT | `/api/transactions/{id}`| Update a specific transaction | ✅ (JWT) |
| DELETE | `/api/transactions/{id}`| Delete a transaction | ✅ (JWT) |

Future Enhancements
- Unit Testing
- Docker
- Refresh Tokens
- Budget Module