Hotel Management backend
-

A modular microservice-based Java backend for hotel management. See the task and requirements below.

At the moment the only service is available:

- reservation

Other services are TBD.

See Swagger API at http://localhost:8080/api/reservation/swagger-ui/index.htmls

#### Task description

Marryat Hotels needs a Property Management System to operate their hotel business. 
Your task is to define and implement a backend REST API using the Java programming language. 
The API should provide functionality to do following:

1. CRUD Reservations
2. Search Reservations by date range (e.g. get reservations from 15 to 19 April 2020)

Reservation is a first-class citizen of the system. 
Reservation entity contains: first & last name of the guest, room number, start date and end date of the stay.

#### Constraints and requirements for implementation

Please follow these rules for your solution:
1. Use Java as a programming language.
2. Use Gradle or Maven as a build tool.
3. Distribute application as a Docker image via hub.docker.com
4. Share the code via github.com

You are also encouraged to use any third-party libraries and frameworks, which may simplify you work.
