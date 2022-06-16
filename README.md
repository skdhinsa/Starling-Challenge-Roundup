# Creating a "round-up" feature

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

