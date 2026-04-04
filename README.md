# SmartSpend API

SmartSpend is a robust Spring Boot REST API designed to help users manage their finances. It features secure JWT-based authentication, comprehensive expense and budget tracking, and leverages Google Gemini AI for intelligent expense analysis.

## Live Demo
API is live at: https://smartspend-api-production.up.railway.app

### Swagger Documentation
https://smartspend-api-production.up.railway.app/swagger-ui/index.html

## Features

- **Secure Authentication:** User registration and login with JWT (JSON Web Tokens) and BCrypt password encryption.
- **Expense Management:** Full CRUD (Create, Read, Update, Delete) operations for tracking daily expenses.
- **Budget Management:** Set, track, and manage monthly or category-based budgets.
- **AI Expense Analysis:** Integrated with Google Gemini AI to provide intelligent insights and analysis on spending habits.
- **Relational Database:** Reliable data persistence using PostgreSQL.

## Tech Stack

- **Framework:** Spring Boot, Spring Security, Spring Data JPA
- **Language:** Java
- **Database:** PostgreSQL
- **Authentication:** JWT (JSON Web Tokens)
- **Encryption:** BCrypt
- **AI Integration:** Google Gemini API
- **Build Tool:** Maven

## Setup Instructions

### Prerequisites

- Java 17+
- Maven
- PostgreSQL
- Google Gemini API Key

### Configuration

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd smartspend-api
   ```

2. **Database Setup:**
   Create a PostgreSQL database (e.g., `smartspend`).

3. **Application Properties:**
   Ensure you have configured your database credentials, JWT secret, and Gemini API key. Check `src/main/resources/application.properties` or create one from an example file if present.
   
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:postgresql://localhost:5432/smartspend
   spring.datasource.username=your_postgres_username
   spring.datasource.password=your_postgres_password
   spring.jpa.hibernate.ddl-auto=update

   # JWT Configuration (Use a strong, long secret key)
   jwt.secret=your_super_secret_jwt_key_here

   # Google Gemini API Key
   gemini.api.key=your_gemini_api_key_here
   ```

### Running the Application

Use Maven to build and run the Spring Boot application:

```bash
mvn clean install
mvn spring-boot:run
```

The server will start on `http://localhost:8080`.

## API Endpoints

Below is a typical list of endpoints for the SmartSpend API. *(Note: Adjust the exact paths if your controllers map them differently, e.g., `/api/v1/...`)*

| Resource | Method | Endpoint | Description | Auth Required |
|----------|--------|----------|-------------|---------------|
| **Auth** | POST | `/api/auth/register` | Register a new user | No |
| **Auth** | POST | `/api/auth/login` | Authenticate user & return JWT | No |
| **Expenses** | POST | `/api/expenses` | Create a new expense | Yes |
| **Expenses** | GET | `/api/expenses` | Get all expenses for user | Yes |
| **Expenses** | GET | `/api/expenses/{id}`| Get specific expense by ID | Yes |
| **Expenses** | PUT | `/api/expenses/{id}`| Update an expense | Yes |
| **Expenses** | DELETE | `/api/expenses/{id}`| Delete an expense | Yes |
| **Budgets** | POST | `/api/budgets` | Create a new budget | Yes |
| **Budgets** | GET | `/api/budgets` | Get all budgets for user | Yes |
| **Budgets** | GET | `/api/budgets/{id}`| Get specific budget by ID | Yes |
| **Budgets** | PUT | `/api/budgets/{id}`| Update a budget | Yes |
| **Budgets** | DELETE | `/api/budgets/{id}`| Delete a budget | Yes |
| **AI Analysis**| GET | `/api/expenses/analysis`| Get Gemini AI expense analysis | Yes |

## Project Structure Highlights

- `controller/`: REST API endpoints mapping.
- `service/`: Core business logic (including Gemini AI service).
- `repository/`: Spring Data JPA repositories for PostgreSQL interaction.
- `entity/`: JPA Entity models (`User`, `Expense`, `Budget`).
- `security/`: JWT Filter, Utility classes, and Security configurations.
- `dto/`: Data Transfer Objects for requests and responses.
