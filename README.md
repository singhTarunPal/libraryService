# libraryService

Its a Spring Boot webservice based on Java

*Design*
 - ITs a Microservice with a controller, Service and DAO Layer (SEparation of concenrs)

*Integrations*
 - It integrated with notification service(Rabbit MQ) asynch
 - It integrated with inventoryService REST API using JSON

*Features*
 - It has Log4j implemented
 - It has Swagger implemented

*API*
 - GET
  http://localhost:8080/library/searchIssuedBook?studentId=< studentID >  
    -Get the Books issued to a student by passing student Id
- GET
  http://localhost:8080/library/issueBook?issueId=< issueId >  
    -Get the Issue (book) details, issued to a student by passing issue Id
 
 - POST
    - http://localhost:8080/library/issueBook    
      - Issue a Book
      - {   "bookISBN": "CSE401",   "issuedTo": "2020MT93553",   "issuedForDays": 15  }
     - http://localhost:8080/library/returnBook    
       - Return a Book
       - {    "issueId": 7,    "bookISBN": "CSE301",    "issuedTo": "2020MT93553",    "issuedForDays": 10 }
