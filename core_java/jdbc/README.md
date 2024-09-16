# Introduction
The CRUD process for a database in a PSQL instance is implemented in this app. In the implementation, the database is accessed through JDBC, the CRUD process is handled by customer DAO, and the PSQL instance is stored in a Docker container.

# Implementaiton
## ER Diagram
![ER_diagram](https://user-images.githubusercontent.com/54780945/187825647-da35bf64-984a-471a-b414-bb8f1f7bbb43.png)

## Design Patterns
The Data Access Object (DAO) pattern is used by this application to segregate the application layer from the database (persistence layer). It does this by defining an abstract API that executes CRUD operations on objects of type T through the use of the DataAccessObject class and DataTransferObject interface. As an outcome, the CustomerDAO and OrderDAO classes offer all the required functionality for the Customer and Order objects, and a clear and comprehensive implementation that interacts with those objects is provided. The low-level details of the way the objects are handled are all hidden by the DAO classes. This application implements the DAO design, which essentially separates the client interface of a data resource from its data access methods. This allows the data access mechanisms to change independently of the code that accesses the data.

# Test
- Writing queries for each method
- Comparing the results to the database using the JDBCExecutor
- Outputting the results to the console
