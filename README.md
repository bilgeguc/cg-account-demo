# cg-account-demo
This is a simple Spring Boot rest api application. Response will return a JSON that will contain result and message, or requested data.

* **

## Requirements
* **
* Java 8
* Spring Boot 2.7.10
* Maven 3.2.0

### Additional Dependencies
* **
* H2 Database
* GSON 2.10.1
* Lombok
* JUnit
* Mockito



* **

# Setup
* Build the project by running `./mvnw clean package` inside cg-account-demo directory.
* Once successfully built, run the service by using the following command:
````
java -jar target\cg-account-demo-0.0.1.jar
````

* **

# REST APIs Endpoints
REST API sample consists of three sample endpoints and once the app is running you can access the context path via URI : `http://localhost:8080/api`

* **

### New Account
To open a new account with an initial credit for an existing customer.
````
POST /account/new
Query Params
- customerID [Long]
- initialCredit [BigDecimal]

Response
Content-Type: application/json
{
    "result":true/false
    "message":String
}
````
If initial credit is greater than zero, `AccountService` also creates a `Transaction` via `TransactionService` and IDs will be provided within the response message. If the `customerID` provided does not map any existing customer than `CustomerNotFoundException` will be thrown.


* **

### Get Customer Info
To retrieve information about any existing customers and their account details.
````
GET /account/customer/{customerID}

Response
Content-Type: application/json
{
    "customerName":String,
    "customerSurname":String,
    "customerId": Long,
    "accounts": Array
    [{
        "account":{
            "accountID": Long,
            "customerID": Long,
            "balance": BigDecimal
        },
        "transactions": Array
        [
            {
                "transactionID": Long,
                "accountID": Long,
                "transactionAmount": BigDecimal,
                "transactionType": TransactionType (ex: CREDIT),
                "transactionDatetime": Timestamp (ex: 2023-04-10T07:21:15.287+00:00)
            }
        ]  
    }]
}
````

If there is no transactions of the accounts then `transactions` field returns an empty array, and if the customer has no accounts then `accounts` field returns an empty array.

* **

### Credit Transaction
Makes a transaction to an existing account of a customer.

````
POST /transaction/credit
Query Params
- accountID [Long]
- credit [BigDecimal]

Response
Content-Type: application/json
{
    "result":true/false
    "message": String
}
````
* **

## Additional
Once the app is up and running, it's preloaded with four sample customers and their IDs [1,2,3,4] respectivly.