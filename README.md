# TEST PROJECT task-apigateway-managment

-- Versión del servidor: 10.3.16-MariaDB
-- Versión de PHP: 7.3.7
- **Java version "1.8.0_241"**
- **Apache Maven 3.6.3**
        https://www-us.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip

- **Database Schema created**: (the default schema name is **gateways_db**)
        
        CREATE DATABASE `gateways_db`
        /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
        /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
        /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
        /*!40101 SET NAMES utf8mb4 */;
       
       
    ###DEVELOPMENT TOOLS USED
    
    - IntelliJ IDEA 2018.1
    - Postman 7.17.0
    - Git Bash
    - Github  
    
    ### HOW TO DOWNLOAD OR CLONE THE REPOSITORY
    
        git clone https://github.com/gyanet/boot-mysql-apigateway-managment.git
        
    ###REGARDING THE PROJECT
     This project has Flyway configured as a tool for database migration.
     - Creation of tables automatically
     - Tables are populated with data
     
     ###UNIT AND INTEGRATION TESTS
      This project uses the Spock framework to perform unit and integration tests.
      
  ###RUNNING THE PROJECT
  1. Using java
      - First execute the following command
              `mvn install`
      - Second execute`mvn spring-boot:run` from the root directory of the project to run the application.
              
  ###WHERE THE API FUNCTIONALITIES ARE DESCRIBED
    This project uses **Swagger** to describe the api's functionalities, therefore, the description of the functionalities of the exposed api's are described in:
    
      http://localhost:8080/v2/api-docs
      
     or
     
      http://localhost:8080/swagger-ui.html
      
  ###HOW TO CONSUME THE FUNCTIONALITIES OF THE API's
  There is a file in the root of the project that contains a Postman collection and that contains all the functionalities of the Api's.
  
      task-gateway.postman_collection.json
  
  In this collection are the functionalities of:
  
Functionality | Method | Parameters | Parameter type | Description
  ---                                | ---    | ---                 | ---          | ---                    |
  /{id}                              | GET    | id                  | PathVariable |Get detail of gateway
  -                                  | GET    | -                   | -            | Get all gateways
  -                                  | POST   | GatewayDto          | Body         | Add a gateway
  /{idGateway}/peripheral/{idDevice} |  POST  | idGateway, idDevice | PathVariable | Add device to gateway
  /{idGateway}/peripheral            | DELETE | idGateway           | PathVariable |Delete device to gateway