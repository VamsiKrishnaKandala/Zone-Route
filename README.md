# Zone-Route Microservices

This project contains the following three services as part of the Zone-Route Microservices:

- ZoneService
- RouteService
- Eureka Server

This document provides an overview of each service, including API endpoints and essential configuration details.

---

# Project Structure
```bash
Zone-Route/
├── eureka-server/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/wastewise/eureka_server/
│   │               └── EurekaServerApplication.java
│   ├── pom.xml
│   └── application.properties
│
├── zone-service/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/wastewise/zoneservice/
│   │       │       ├── ZoneServiceApplication.java
│   │       │       ├── client/
│   │       │       │   ├── RouteClient.java
│   │       │       │   └── RouteClientFallback.java
│   │       │       ├── config/
│   │       │       │   └── SwaggerConfig.java
│   │       │       ├── controller/
│   │       │       │   └── ZoneController.java
│   │       │       ├── dto/
│   │       │       │   ├── ZoneCreationRequest.java
│   │       │       │   ├── ZoneUpdateRequest.java
│   │       │       │   └── ZoneResponse.java
│   │       │       ├── entity/
│   │       │       │   └── Zone.java
│   │       │       ├── exception/
│   │       │       │   ├── GlobalExceptionHandler.java
│   │       │       │   └── custom/
│   │       │       │       ├── ZoneNotFoundException.java
│   │       │       │       ├── DuplicateZoneNameException.java
│   │       │       │       ├── NoZoneChangesDetectedException.java
│   │       │       │       └── ZoneDeletionException.java
│   │       │       ├── repository/
│   │       │       │   └── ZoneRepository.java
│   │       │       ├── service/
│   │       │       │   ├── ZoneService.java
│   │       │       │   └── impl/
│   │       │       │       └── ZoneServiceImpl.java
│   │       │       └── util/
│   │       │           └── ZoneIdGenerator.java
│   ├── pom.xml
│   └── application.properties
│
├── route-service/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/wastewise/routeservice/
│   │       │       ├── RouteServiceApplication.java
│   │       │       ├── config/
│   │       │       │   └── SwaggerConfig.java
│   │       │       ├── controller/
│   │       │       │   └── RouteController.java
│   │       │       ├── dto/
│   │       │       │   ├── RouteCreationRequest.java
│   │       │       │   ├── RouteUpdateRequest.java
│   │       │       │   └── RouteResponse.java
│   │       │       ├── entity/
│   │       │       │   └── Route.java
│   │       │       ├── exception/
│   │       │       │   ├── GlobalExceptionHandler.java
│   │       │       │   └── custom/
│   │       │       │       ├── RouteNotFoundException.java
│   │       │       │       ├── DuplicateRouteNameException.java
│   │       │       │       ├── ZoneNotFoundException.java
│   │       │       │       ├── InvalidRouteDetailsException.java
│   │       │       │       └── NoRouteChangesDetectedException.java
│   │       │       ├── feign/
│   │       │       │   └── ZoneClient.java
│   │       │       ├── repository/
│   │       │       │   └── RouteRepository.java
│   │       │       ├── service/
│   │       │       │   ├── RouteService.java
│   │       │       │   └── impl/
│   │       │       │       └── RouteServiceImpl.java
│   │       │       └── util/
│   │       │           └── RouteIdGenerator.java
│   ├── pom.xml
│   └── application.properties
│
└── README.md
```

# Getting Started

### 1. Open a Terminal

Open a terminal or command prompt on your computer where Git is installed.

### 2. Run the Git Clone Command

```bash
git clone https://github.com/VamsiKrishnaKandala/Zone-Route.git
```

### 3. Navigate to the Cloned Directory

After cloning, move into the project directory by running:

```bash
cd Zone-Route
```

### 4. Verify the Repository

Ensure the repository is cloned correctly by listing the files in the directory:

**Mac/Linux:**

```bash
ls
```

**Windows:**

```bash
dir
```

You should see the following folders:

* eureka-server/
* zone-service/
* route-service/
* README.md

---

### Prerequisites

Ensure you have the following installed on your system:

- Java 11 or higher
- Maven (to manage project dependencies)
- MySQL Database (or another DBMS if configured differently)
- A modern IDE like IntelliJ IDEA for development.
---

# Installation and Running Services

You need to build and run each service individually.

---

### Build the Project

From the root folder or inside each service folder, run:

```bash
mvn clean install
```

---

### Run Services

Run each microservice separately:

* For Eureka Server:

```bash
cd eureka-server
mvn spring-boot:run
```

* For Zone Service:

```bash
cd zone-service
mvn spring-boot:run
```

* For Route Service:

```bash
cd route-service
mvn spring-boot:run
```

---

# Database Configuration

### 1. Create the Database

Open your MySQL client and create a new database:

```sql
CREATE DATABASE wastewise_zr;
```

### 2. Run Schema Scripts

Run the schema SQL scripts for Zone and Route services:

```sql
SOURCE /path/to/zone-service/db/schema.sql;
SOURCE /path/to/route-service/db/schema.sql;
```

### 3. Update Database Credentials

Update `application.properties` for each service (zone-service and route-service):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/wastewise_zr
spring.datasource.username=<YOUR_USERNAME>
spring.datasource.password=<YOUR_PASSWORD>
spring.jpa.hibernate.ddl-auto=update
```

---

# Seed Test Data (Optional)

You can populate initial data by running the data SQL scripts:

```sql
SOURCE /path/to/zone-service/db/data.sql;
SOURCE /path/to/route-service/db/data.sql;
```

---
# Zone Service – WasteWise

The Zone Service is designed to manage and schedule zones efficiently. This document provides an overview of the project, including how to clone the repository, set up the database, and get started with the project.

## Features

- Manage zones within the WasteWise system.
- Integration with Route Service to fetch routes assigned to zones.
- APIs to interact with the module for seamless integration with other systems.
- Swagger/OpenAPI documentation for the Zone Service.
- Global exception handling for various API operations.

## Table of Contents

- Usage  
- API Endpoints  
- HTTP Status Codes  
- Notes  

---
## Usage
### API Endpoints
The following REST API endpoints are available in the Zone Service. These endpoints allow you to create, delete, retrieve, and list zones.

### Base URL
All endpoints are prefixed with: http://localhost:8080/wastewise/admin/zones

### Endpoints

**1. Create a New Zone**

- **Description:** Creates a new zone resource. 

- **Method:** POST URL: /wastewise/admin/zones/create

**Request Body:**

```bash
{
  "zoneName": "Zone A",
  "areaCoverage": 1000
}
```
**Response:**
```bash
{
  "message": "New zone created with ID",
  "zoneId": "Z001"
}
```

### 2. Delete a Zone

- **Description:** Deletes an existing zone by its ID. 

- **Method:** DELETE URL: /wastewise/admin/zones/delete/{zoneId} 

**Path Parameter:**

- zoneId (String): The ID of the zone to delete.
  
**Response:**

```bash
{
  "message": "Zone deleted successfully"
}
```
### 3. List All Zones

- **Description:** Retrieves a list of all existing zones.

- **Method:** GET URL: /wastewise/admin/zones/list 

**Response:**
```bash
[
  {
    "zoneId": "Z001",
    "zoneName": "Zone A",
    "areaCoverage": 1000
  },
  {
    "zoneId": "Z002",
    "zoneName": "Zone B",
    "areaCoverage": 2000
  }
]
```
### 4. Get Zone by ID

- **Description:** Retrieves a specific zone by its ID.

- **Method:** GET URL: /wastewise/admin/zones/{zoneId} 

**Path Parameter:**

- zoneId (String): The ID of the zone to retrieve.
**Response:**
  ```bash
  {
  "zoneId": "Z001",
  "zoneName": "Zone A",
  "areaCoverage": 1000
}

## HTTP Status Codes
These endpoints use the following HTTP status codes:

- **200 OK:** The request was successful.
- **201 Created:** A new resource was successfully created.
- **204 No Content:** The resource was successfully deleted.
- **400 Bad Request:** The request was invalid (e.g., invalid data or missing fields).
- **404 Not Found:** The requested resource could not be found.
- **500 Internal Server Error:** An unexpected error occurred on the server.

## Examples
### Create a New Zone
```bash
curl -X POST http://localhost:8080/wastewise/admin/zones/create \
-H "Content-Type: application/json" \
-d '{
      "zoneName": "Zone A",
      "areaCoverage": 1000
    }'
```
**Response:**
```bash
{
  "message": "New zone created with ID",
  "zoneId": "Z001"
}
```
**Delete a Zone**

```bash
curl -X DELETE http://localhost:8080/wastewise/admin/zones/delete/Z001
```
**List All Zones**
```bash
curl -X GET http://localhost:8080/wastewise/admin/zones/list
```
**Response:**
```bash
[
  {
    "zoneId": "Z001",
    "zoneName": "Zone A",
    "areaCoverage": 1000
  },
  {
    "zoneId": "Z002",
    "zoneName": "Zone B",
    "areaCoverage": 2000
  }
]
```
**Get Zone by ID**
```bash

curl -X GET http://localhost:8080/wastewise/admin/zones/Z001
```
**Response:**
```bash
{
  "zoneId": "Z001",
  "zoneName": "Zone A",
  "areaCoverage": 1000
}
```

# Notes
- Replace {zoneId} in the URL with the actual ID of the zone you want to access.
- Ensure the application is running locally or on the specified host/port before accessing these endpoints.
- For detailed API testing, tools like Postman, cURL, or Swagger can be used.
---
# Route Service – WasteWise

The Route Service is designed to manage and schedule routes efficiently. This document provides an overview of the project, including how to clone the repository, set up the database, and get started with the project.

## Features

- Manage routes within the WasteWise system.
- Integration with Zone Service to fetch zones assigned to routes.
- APIs to interact with the module for seamless integration with other systems.
- Swagger/OpenAPI documentation for the Route Service.
- Global exception handling for various API operations.

## Table of Contents

- Usage  
- API Endpoints  
- HTTP Status Codes  
- Notes  

---

## Usage
### API Endpoints
The following REST API endpoints are available in the Route Service. These endpoints allow you to create, delete, retrieve, and list routes.

### Base URL
All endpoints are prefixed with: http://localhost:8081/wastewise/admin/routes

### Endpoints

**1. Create a New Route**

- **Description:** Creates a new route resource.
- 
- **Method:** POST URL: /wastewise/admin/routes/create
- 
**Request Body:**

```bash
{
  "zoneId": "Z001",
  "routeName": "Route A",
  "pickupPoints": "Point1, Point2",
  "estimatedTime": 30
}
```
**Response:**
```bash
{
  "routeId": "Z001-R001",
  "zoneId": "Z001",
  "routeName": "Route A",
  "pickupPoints": "Point1, Point2",
  "estimatedTime": 30
}
```

### 2. Delete a Route

- **Description:** Deletes an existing route by its ID.
  
- **Method:** DELETE URL: /wastewise/admin/routes/delete/{routeId}
  
**Path Parameter:**

- routeId (String): The ID of the route to delete.
  
**Response:**

```bash
{
  "message": "Route deleted successfully"
}
```
### 3. List All Routes
**Description:** Retrieves a list of all existing routes.
**Method:** GET URL: /wastewise/admin/routes/list 
**Response:**
```bash
[
  {
    "routeId": "Z001-R001",
    "zoneId": "Z001",
    "routeName": "Route A",
    "pickupPoints": "Point1, Point2",
    "estimatedTime": 30
  },
  {
    "routeId": "Z001-R002",
    "zoneId": "Z001",
    "routeName": "Route B",
    "pickupPoints": "Point3, Point4",
    "estimatedTime": 45
  }
]
```
### 4. Get Route by ID
**Description:** Retrieves a specific route by its ID.
**Method:** GET URL: /wastewise/admin/routes/{routeId} 
**Path Parameter:**

- routeId (String): The ID of the route to retrieve.
**Response:**
```bash
{
  "routeId": "Z001-R001",
  "zoneId": "Z001",
  "routeName": "Route A",
  "pickupPoints": "Point1, Point2",
  "estimatedTime": 30
}
```
### 5. Get Routes by Zone ID
**Description:** Retrieves all route IDs for a given zone.
**Method:** GET URL: /wastewise/admin/routes/zone/{zoneId} 
**Path Parameter:**

- zoneId (String): The ID of the zone to retrieve routes for.
**Response:**
```bash
[
  "Z001-R001",
  "Z001-R002"
]
```

## HTTP Status Codes
These endpoints use the following HTTP status codes:

- **200 OK:** The request was successful.
- **201 Created:** A new resource was successfully created.
- **204 No Content:** The resource was successfully deleted.
- **400 Bad Request:** The request was invalid (e.g., invalid data or missing fields).
- **404 Not Found:** The requested resource could not be found.
- **409 Conflict:** The request could not be completed due to a conflict with the current state of the resource.
- **500 Internal Server Error:** An unexpected error occurred on the server.

## Examples
### Create a New Route
```bash
curl -X POST http://localhost:8081/wastewise/admin/routes/create \
-H "Content-Type: application/json" \
-d '{
      "zoneId": "Z001",
      "routeName": "Route A",
      "pickupPoints": "Point1, Point2",
      "estimatedTime": 30
    }'
```
**Response:**
```bash
{
  "routeId": "Z001-R001",
  "zoneId": "Z001",
  "routeName": "Route A",
  "pickupPoints": "Point1, Point2",
  "estimatedTime": 30
}
```
**Delete a Route**

```bash
curl -X DELETE http://localhost:8081/wastewise/admin/routes/delete/Z001-R001
```
**List All Routes**
```bash
curl -X GET http://localhost:8081/wastewise/admin/routes/list
```
**Response:**
```bash
[
  {
    "routeId": "Z001-R001",
    "zoneId": "Z001",
    "routeName": "Route A",
    "pickupPoints": "Point1, Point2",
    "estimatedTime": 30
  },
  {
    "routeId": "Z001-R002",
    "zoneId": "Z001",
    "routeName": "Route B",
    "pickupPoints": "Point3, Point4",
    "estimatedTime": 45
  }
]
```
**Get Route by ID**
```bash
curl -X GET http://localhost:8081/wastewise/admin/routes/Z001-R001
```
**Response:**
```bash
{
  "routeId": "Z001-R001",
  "zoneId": "Z001",
  "routeName": "Route A",
  "pickupPoints": "Point1, Point2",
  "estimatedTime": 30
}
```
**Get Routes by Zone ID**
```bash
curl -X GET http://localhost:8081/wastewise/admin/routes/zone/Z001
```
**Response:**
```bash
[
  "Z001-R001",
  "Z001-R002"
]
```

# Notes
- Replace {routeId} and {zoneId} in the URL with the actual ID of the route or zone you want to access.
- Ensure the application is running locally or on the specified host/port before accessing these endpoints.
- For detailed API testing, tools like Postman, cURL, or Swagger can be used.
---
# Eureka Server – WasteWise

The Eureka Server is designed to manage service discovery efficiently within the WasteWise system. This document provides an overview of the project, including how to clone the repository, set up the server, and get started with the project.

## Features

- Service discovery for microservices within the WasteWise system.
- Integration with various microservices for seamless communication.
- APIs to interact with the Eureka Server for service registration and discovery.
- Global exception handling for various API operations.

## Table of Contents
 
- Usage  
- Notes  

---


## Usage
### Service Registration and Discovery
The Eureka Server allows microservices to register themselves and discover other services. Ensure that your microservices are configured to register with the Eureka Server.

### Base URL
Access the Eureka Server dashboard at: http://localhost:8761

### Notes
- Ensure the Eureka Server is running locally or on the specified host/port before accessing the dashboard.
- For detailed API testing, tools like Postman, cURL, or Swagger can be used.
- Make sure your microservices have the necessary configurations to register with the Eureka Server.
