# Starling Tech Challenge - "round-up" feature

## API:
* **PUT**: http://localhost:8080/api/v1/feed/account/{accountUid}/savings-goals/round-up/transactions-between

Created a PUT endpoint for the 'round-up' feature for transactions in any given week.
* Path parameter:
  - accountUid

* Query parameters:
  - minTransactionTimestamp UTC
    - Example : 2020-06-01T12:34:56.000Z
  - maxTransactionTimestamp UTC
    - Example : 2020-07-01T12:34:56.000Z

Can use an API testing tool, Postman for instance, to make a request to the roundup endpoint.
Can then use relevant savings-goal endpoints to see updates made.

## 1. Get an Access Token:
 - On the Starling Bank Developers Account, generate a new access token.
 - Replace the access token found in _"application.properties"_ called **bearerToken**

## 2. Compile and Run the application:
Ensure to cd into roundup directory.
Run _"mvn clean install"_ in the terminal.
```
cd roundup
mvn clean install
```

* To run the application:
  - Navigate to **RoundupApplication.java**.
  - Run the application within an IDE or using the terminal command:
```
mvn spring-boot:run
```

* To run the tests:
  - Navigate to  **RoundupApplicationTests.java** class in test directory and run.
  - Or run following command in terminal:
```
mvn test
```

## Improvements:
- Dockerize the application
- Add a database connection to hold the 'transactionUid' for everytime transaction in a given week are rounded up and swept into a Savings goal. To keep track of each sweep.
- Would likely include the bearerToken as part of the request to this endpoint - in 'Authorization' header
- Improve upon error handling and exceptions inclusion
- Inject the public API URLs in a better fashion - either a different class, in application.properties, using URI component builder.
- Validation for the dates passed in the request to ensure they are a week apart. 
- Have OpenAPI Specification for this endpoint
- Consider implementation of circuit breaker pattern - due to dependency on multiple external APIs (or microservices in the actual infrastructure)
- The addition of integration tests.

## Requirement Questions:
* On requirement to create a new savings goal: to create a new savings goal for every single round-up?
  - Within this application a savings goal called "Round-up Savings" is created once only (if it doesn't exist) and then all "round-ups" will be sweeped into that account.


* On requirement to create a new savings goal: is there a default amount for target minor units?
  - In this application it is set to '10000', however, if the /roundup API is to create a savings goal, are there default values to input on behalf of the customer?


* On account types that are eligible for 'weekly transaction round up":
  - There appears to be 3 account types: [PRIMARY, ADDITIONAL, LOAN]. Does this feature only apply to 'PRIMARY' accounts? In this application, it has only considered PRIMARY accounts, as that is where it is presumed most spending would occur. However, this can be modified if other types included also.


* On the spending category:
  - Which categories for transactions are eligible for "round-up"?


* On customer choosing which savings goal:
  - The requirements say to create a new savings goal, (which implies this occurs everytime the API is called). However, is there an option to allow the customer to choose from current savings goal or to create a new savings goal? 


* On the HTTP Method:
  - Given that the requirements do not specify whether we are retrieving any information or performing an update. In this application it is a PUT endpoint, as we are updating a savings goal or creating one.

