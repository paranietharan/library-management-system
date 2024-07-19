# Library Management System Backend

## Overview

This repository contains the backend code for the Library Management System, a web application designed for school students. The backend is built using Spring Boot and provides RESTful APIs to support various library operations.

## Technologies

- Spring Boot
- MySQL
- REST API
- Maven

## Features

- User Authentication and Management
- Book Lending and Reservation
- Fine Management
- Article Management (Add, Edit, Delete)
- Complaint Management
- Email Service for Notifications

## Installation

### Prerequisites

- Java (>= 11)
- Maven
- MySQL

### Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/library-management-system-backend.git
    cd library-management-system-backend
    ```

2. Configure the MySQL database:
    - Create a new database named `library_management`.
    - Update the `application.properties` file in the `src/main/resources` directory with your MySQL configuration:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/library_management
      spring.datasource.username=root
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      ```

3. Install dependencies and build the project:
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Usage

- The backend server will start at `http://localhost:8080`.
- Use the provided APIs to interact with the system.
