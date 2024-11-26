# HOU, Mate Finder App for Vermintide 2.

HOU is an app designed to help players find teammates for Vermintide 2. It helps you connect with others, join or form teams, and manage game sessions.

# Website link 
https://heroesofubershraik-0dd9d2d4dd3b.herokuapp.com/login?logout

Features:

- **Profile Creation**: Add your class, country, skills, etc.
- **Create a Team**: Create your team with your preferred mode and style of play and specify which players you are looking for.
- **Find Teams**: Browse and join teams that match your playstyle.
- **Join Teams**: Apply to teams you're interested in.
- **Team Management**: Accept or reject team applications.
- **Chat**: Communicate with your team in a dedicated chat.
- **Admin Controls**: Add or remove members from your team.

> **Note**: The app does not adapt well to phones and small screens.

## Screenshots

## Login Page
![App Screenshot](src/main/resources/static/screeenshots/Login.PNG)

## Registration page
![App Screenshot](src/main/resources/static/screeenshots/Register.PNG)

## Main Page
![App Screenshot](src/main/resources/static/screeenshots/MainPage.PNG)

## Team List
![App Screenshot](src/main/resources/static/screeenshots/teamList.PNG)

## Team Chat
![App Screenshot](src/main/resources/static/screeenshots/teamChat.PNG)




# Technologies Used in the Project

This project utilizes the following technologies:

## Core Technologies
- **Java 17**: Programming language.
- **Spring Boot 3.3.4**: Framework for building web applications.
- **PostgreSQL**: Relational database.

## Dependencies
1. **Spring Boot Starter Web**  
   - For creating REST APIs and web applications.

2. **Spring Boot Starter Thymeleaf**  
   - For working with HTML templates.

3. **Spring Boot Starter Data JPA**  
   - For database interactions using JPA (Hibernate).

4. **Spring Boot Starter Security**  
   - For implementing authentication and authorization.

5. **Spring Boot Starter Validation**  
   - For validating input data.

6. **Spring Boot Starter Websocket**  
   - For real-time bidirectional communication via WebSocket.

7. **Thymeleaf Extras Spring Security 6**  
   - For integrating Spring Security features into Thymeleaf templates.

8. **PostgreSQL JDBC Driver**  
   - For connecting to the PostgreSQL database.

## Tools and Build
- **Maven**: Dependency and build management tool.
- **Spring Boot Maven Plugin**: Simplifies building and running Spring Boot applications.


## Installation Guide

### 1. Clone the Repository

Clone the repository to your local machine using the following command: git clone https://github.com/MattIRio/HOU.git

Navigate into the project directory: cd HOU

### 2. Install Dependencies

Ensure that **Maven** is installed on your system. You can download Maven from [here](https://maven.apache.org/download.cgi).

Install the project dependencies using Maven: mvn install


### 3. Set Up PostgreSQL Database

This project uses **PostgreSQL** for data storage. Make sure PostgreSQL is installed on your machine. You can download it from [here](https://www.postgresql.org/download/).

Create a new database and update the database connection settings in the `application.properties` file: 
spring.datasource.url=jdbc:postgresql://localhost:5432/HOU_users_database
spring.datasource.username=postgres
spring.datasource.password=1234

### 4. Run the Application

After installing dependencies and setting up the database, run the application:mvn spring-boot:run

### 5. Access the Application

Once the application is up and running, open your browser and go to: http://localhost:8080


