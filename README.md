# QMoney
Portfolio application
Stock Annualized Returns Calculator

Overview
This project processes stock details from a JSON file and an end date as input. It fetches stock price data from third-party APIs (Tiingo and AlphaVantage) and calculates annualized returns based on purchase and end dates.

Features
Fetches stock price data from Tiingo & AlphaVantage APIs
Calculates annualized returns based on stock purchase and end dates
Implements Factory Design Pattern to switch between different API providers
Supports multithreading for efficient data fetching
Exception handling and robust error management
Unit tested for reliability
Tech Stack & Skills Used
Java, Spring Boot, REST APIs
HTTP Requests, Jackson, Multithreading
Factory Design Pattern, Code Refactoring
Gradle, Unit Testing, Exception Handling
How It Works
Provide a JSON file with stock details and an end date as input.
The system fetches historical stock prices using Tiingo/AlphaVantage APIs.
It calculates annualized returns based on stock purchase and end prices.
Returns the processed results in a structured format.
Setup & Usage
Clone the repository
sh

git clone <repo-url>
cd <project-directory>
Build the project
sh

./gradlew build
Run the application
sh

java -jar build/libs/<your-app>.jar
Provide input JSON file & end date
Future Enhancements
Add more stock data providers
Implement caching for API responses
Improve error handling and logging
