# REST API Todos
## General info
REST API for scheduling tasks. Allows you to focus on important and urgent things. Tasks can be organized into lists and projects.

## Technologies
* Java 21
* Spring Boot 3.4.3
* Spring Data JPA
* Spring Security
* MySQL 8.0
* Apache Maven
* Project Lombok
* JSON Web Token (jjwt) 0.11.5

## Setup and Installation

1. #### Download or clone the repository from GitHub
```
git clone https://github.com/sm1l43s/Backend-Todos.git
cd Backend-Todos
```
2. #### Install required programs

In order to follow along user needs to have MySQL and Postman.
<br>
Bellow are short terminal lines for easy installation for Linux systems.
```
sudo apt update
sudo snap install postman
sudo apt install mysql-server
```

3. #### Run the spring boot application
If you download/clone repo elsewhere, change path update accordingly
```
cd Backend-Rodos
mvn clean install
java -jar target/rest_api_todos-0.0.1.jar
```
this runs at port 8080 and hence all endpoints can be accessed starting from http://localhost:8080

4. #### Load Database with Sample Data


1. Start MySQL via Docker

Make sure Docker is installed and run docker-compose.yml in the root of the project:

docker-compose up -d

2. Open MySQL Command Line

Using MySQL 8.0 Command Line Client (or any DB tool like DBeaver):

3. Select and use the database:

SHOW DATABASES;
USE todo_rest_api;

4. Import structure or structure with test data

* Only structure

SOURCE C:/IntelliJ IDEA workspace/Backend-Todos/src/main/resources/database/todo_rest_api_only_structure.sql;
* Structure with test data

SOURCE C:/IntelliJ IDEA workspace/Backend-Todos/src/main/resources/database/todo_rest_api_with_data.sql;

5. Check that data was inserted:

SHOW TABLES;
SELECT * FROM roles;

You are now ready to explore the API!

