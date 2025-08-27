# Fingerprint Capture & Session Management API

## Project Overview
This project is a backend API for managing fingerprint capture sessions. It allows creating sessions for users/devices, uploading fingerprint images, retrieving captures, and generating session-level reports. The API is built using **Spring Boot** and uses **API key-based authentication** for security.

---

## APIs

### Session APIs
- **POST /sessions** – Create a new session (requires `userId` and `deviceId` in request body).  
- **GET /sessions/{sessionId}** – Fetch details of a session by its UUID.

### Capture APIs
- **POST /captures** – Upload a fingerprint capture (multipart form-data with `image` and `sessionId`).  
- **GET /captures/{captureId}** – Get a capture by its UUID.  
- **GET /captures/user/{userId}** – Get all captures for a user.  
- **GET /captures/session/{sessionId}** – Get all captures for a session.  
- **GET /captures/device/{deviceId}** – Get all captures from a device.

### Report API
- **GET /reports/session/{sessionId}** – Generate a report for a specific session.

---

## Security
All APIs (except Swagger, H2 console, and uploads) require an API key in the header:


The API key is stored in a `.env` file in the project root.

---

## Environment Setup (.env)
Create a `.env` file in the root of the project with the following content:

API_KEY=12345


Replace `12345` with your desired API key.

---

## Swagger UI
You can explore and test all APIs using Swagger UI at: http://localhost:8080/swagger-ui/index.html


The OpenAPI JSON is available at: http://localhost:8080/v3/api-docs

---

## H2 Database Console
Access the H2-console at: http://localhost:8080/h2-console

- **JDBC URL:** `jdbc:h2:file:./data/demo-db`
- **Username:** `sa`
- **Password:** leave empty

---

## Running the Project
1. Clone the repository.
2. Create the `.env` file as mentioned above.
3. Make sure Java 17+ and Maven are installed.
4. Build the project:

```bash
mvn clean install
```

Run the project: 
```bash
mvn spring-boot:run
```

The server will start on http://localhost:8080.

Use Swagger UI or Postman to test APIs.




