# Currency Conversion API

A Spring Boot application that provides a REST API for currency conversion. This project was created to demonstrate the application of design patterns in a real-world algorithm.

## Overview

The Currency Conversion API allows users to convert amounts from Brazilian Real (BRL) to other currencies like US Dollar (USD) and Euro (EUR). The application implements the Strategy Pattern to handle different conversion strategies based on the source and target currencies.

## Technologies Used

- Java 24
- Spring Boot 3.5.0
- Maven
- RESTful API

## Features

- Convert BRL to USD
- Convert BRL to EUR
- Extensible architecture for adding new currency conversions

## Project Structure

The project follows a clean architecture with the following components:

- **Controller**: Handles HTTP requests and responses
- **Service**: Contains the business logic for currency conversion
- **Strategy Resolver**: Selects the appropriate conversion strategy based on input
- **Converters**: Implement specific conversion algorithms for different currency pairs
- **DTOs**: Data Transfer Objects for API input and output

## Getting Started

### Prerequisites

- Java 24 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/conversion-api.git
   cd conversion-api
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`.

## API Documentation

### Convert Currency

Converts an amount from one currency to another.

**Endpoint**: `GET /conversions`

**Query Parameters**:
- `from`: Source currency code (e.g., BRL)
- `to`: Target currency code (e.g., USD, EUR)
- `amount`: The amount to convert

**Example Request**:
```
GET /conversions?from=BRL&to=USD&amount=100
```

**Example Response**:
```json
{
  "conversion": 18.0505,
  "formattedConversion": null,
  "fromCurrencySign": null,
  "toCurrencySign": null
}
```

## Current Exchange Rates

The application uses the following fixed exchange rates:
- 1 USD = 5.54 BRL
- 1 EUR = 6.40 BRL

## Adding New Converters

To add a new currency converter:

1. Create a new class that implements the `CurrencyConverter` interface
2. Implement the `convert` method with the appropriate conversion logic
3. Add the new converter to the `ConversionStrategyResolver`

Example:
```java
@Service
public class BRLtoGBPConverter implements CurrencyConverter {
    @Override
    public ConversionResponse convert(BigDecimal amount) {
        return new ConversionResponse(
                amount.divide(new BigDecimal("7.50"), 4, RoundingMode.HALF_UP)
        );
    }
}
```

## License

This project is open source and available under the [MIT License](LICENSE).

## Contributors

- Your Name - Initial work