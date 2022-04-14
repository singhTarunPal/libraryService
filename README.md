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
 - It has JWT Token implemented
 - All APIs are versioned
 - it incorporates SAGA pattern for issue/return of book.

*API*
 - POST
    - http://localhost:8081/api/library/authenticate    
       - authenticate a user
       - {   "username": "2020MT93553@wilp.bits-pilani.ac.in",   "password": "123456"}
       
 - GET
    - http://localhost:8081/api/library/v1/searchIssuedBook?studentId=<studentEmailId >  
       - Get the Books issued to a student by passing student Email Id
- GET
     - http://localhost:8081/api/library/v1/issueBook?issueId=< issueId >  
       - Get the Issue (book) details, issued to a student by passing issue Id
 
 - POST
    - http://localhost:8081/api/library/v1/issueBook    
       - Issue a Book
       - {   "bookId": "1",   "issuedToEmailId": "2020MT93553@wilp.bits-pilani.ac.in",   "issuedForDays": 15  }
     - http://localhost:8081/api/library/v1/returnBook    
       - Return a Book
       - {    "issueId": 11,    "bookId": "1",    "issuedToEmailId": "2020MT93553@wilp.bits-pilani.ac.in",   }
