# Result Registration System (D0031N)

A RESTful web service built with Spring Boot that acts as an integration layer for a student result registration system.

## Project Overview

This project is a prototype of a registration system for student results. Its core purpose is to connect a simple web GUI with four separate, conceptual backend services, each backed by its own dedicated database. This simulates a real-world microservice environment where different systems manage their own data.

The application provides a user interface for a teacher to select a course and an assignment, view the enrolled students with their grades from the learning platform (Canvas), and then register a final grade in the official reporting system (Ladok) including the personnumber that is not stored in Canvas.

## System Architecture

The application uses separate data source configurations to manage isolation between the four services.

*   **Canvas Service (`canvasdb`)**: Manages course enrollments and grades from the learning platform. It acts as the source for which students are in a course and what their preliminary grades are.
*   **Epok Service (`epokdb`)**: Manages official course and module data, such as module codes and descriptions.
*   **StudentITS Service (`studentdb`)**: Manages student identity data (name, username, personnummer).
*   **Ladok Service (`ladokdb`)**: The final registration target for official grades and examination dates.

> **Why Spring Boot for Integration?**
>
> Spring Boot is excellent for building such integration applications because it prioritizes convention over configuration, allowing for the rapid creation of stand-alone, production-ready RESTful web services. Its powerful dependency injection and auto-configuration make it the ideal central gateway for connecting disparate systems. This project specifically leverages Spring Boot's capability to manage multiple separate databases within a single application instance, simplifying the complex data isolation required by the student result registration process.

## Databases and H2 Console

This project uses four separate H2 in-memory databases to simulate the isolated services. This means all data is reset every time the application restarts.

You can inspect the data in each database using the built-in H2 console.

1.  Make sure the application is running.
2.  Navigate to [http://localhost:8080/h2-console](http://localhost:8080/h2-console) in your browser.
3.  On the login screen, use the default username `sa` and leave the password blank.
4.  To connect to a specific database, copy one of the following URLs into the **JDBC URL** field:

    *   **Canvas DB**: `jdbc:h2:mem:canvasdb`
    *   **Epok DB**: `jdbc:h2:mem:epokdb`
    *   **StudentITS DB**: `jdbc:h2:mem:studentdb`
    *   **Ladok DB**: `jdbc:h2:mem:ladokdb`

5.  Click "Connect". You can now run SQL queries against the selected database.


## Data Initialization

For demonstration purposes, each database is populated with sample data upon application startup. This is handled by `CommandLineRunner` beans located in each service package (e.g., `CanvasDataInitializer.java`, `StudentDataInitializer.java`). These initializers are responsible for creating the initial set of courses, modules, students, and grades that you see in the GUI and can inspect via the H2 console. This ensures the application is immediately usable without any manual data setup.

## Getting Started

Follow these steps to build and run the application locally.

### Prerequisites

*   Java Development Kit (JDK) 17 or newer.
*   Apache Maven 3.6+

### Installation and Running

1.  **Build the Application**
    This command cleans the project, compiles the code, runs tests, and packages the application into an executable JAR file in the `target/` directory.
    ````bash
    mvn clean install
    ````

2.  **Run the Server**
    This command starts the Spring Boot application. The server will run on `http://localhost:8080`.
    ````bash
    mvn spring-boot:run
    ````
    When you see the log message `Started Application in X seconds`, the server is ready.

3.  **Use the Client GUI**
    Open your web browser and navigate to:
    [http://localhost:8080](http://localhost:8080)

