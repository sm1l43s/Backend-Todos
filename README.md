# REST API Todos
## General info
REST API scheduling tasks. Its allows you to focus on the important and urgent
things. Tasks can be collected into lists and projects, marked with different colors and levels
of importance.

## Technologies
* Java - version 8
* Spring Boot - version 2.1.4
* Spring Data JPA - version 2.1.4
* Spring Security - version 2.1.4
* MySQL - version 5.7
* Apache Maven - version 3.9.1
* Project Lombok
* jsonwebtoken - version 0.8.0

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
3. #### (Optional) Update database configurations in application.properties
If you have changed defualt user for creating database with some different username and password, update the src/main/resources/application.properties file accordingly:
```
spring.jpa.hibernate.ddl-auto=update #for first time running MUST be set to create, for every consecutive time set to update (if you care to have permanent database, otherwise it is deleted after every consecutive jar run)
spring.datasource.url=jdbc:mysql://localhost:3306/todo_rest_api
spring.datasource.username=root
spring.datasource.password=root
```

4. #### Run the spring boot application
If you download/clone repo elsewhere, change path update accordingly
```
cd Backend-Rodos
mvn clean install
java -jar target/rest_api_todos-0.0.1.jar
```
this runs at port 8080 and hence all endpoints can be accessed starting from http://localhost:8080

6. #### Create database objects (If you want some prerecorded values in local database)
