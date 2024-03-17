# Library service

>This is a simple library project that allows you to manage a collection of books, specifying authors and genres, including adding new books, authors and genres, editing existing ones and deleting outdated entries. 

## Technologies Used
- Java 17
- Spring Boot
- Maven

## Installation
1. Clone the repository
2. Create an application.properties file and add DB parameters:
```properties
spring.datasource.url=jdbc:postgresql://localhost:PORT/YOUR_DB_NAME
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=create
```
3. Build the project and run the application
The application will start on `http://localhost:8080`

## API Endpoints
---
### Сonvert a certain amount of currency
#### Request
* Structure:
`localhost:8080/convert/{fromCurrency}/{toCurrency}/{amount}`

* Example:
`localhost:8080/convert/BYN/USD/1`
#### JSON response
```JSON
{
    "status": "success",
    "updated_date": "2024-02-28",
    "base_currency_code": "BYN",
    "amount": 1.0,
    "base_currency_name": "Belarusian ruble",
    "rates": {
        "USD": {
            "currency_name": "United States dollar",
            "rate": "0.3087",
            "rate_for_amount": "0.3087"
        }
    }
}
```
---
