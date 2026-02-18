# Rate CS Teaching Staff

A web application for rating computer science teaching staff members.

## Features

- View all staff ratings
- Add new ratings with validation
- Edit existing ratings
- Delete ratings
- Server-side validation

## Technology

- Java 21 + Spring Boot
- Thymeleaf templates
- PostgreSQL (production) / H2 (development)
- Maven

## Running Tests

```bash
mvn test
```

## Validation Rules

- Email must be valid format
- Scores: 1-10 integers
- Name: 2-100 characters
- Comment: max 500 characters
- Email must be unique

## Known Issues

- Email uniqueness error message could be clearer
- No pagination for large datasets for api requests
- No filtering or sorting options
