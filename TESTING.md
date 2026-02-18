# Testing Documentation

## Testing Approach

This project uses **slice testing** with Spring Boot test annotations:
- `@WebMvcTest` for controller tests
- `@DataJpaTest` for repository tests
- Standard JUnit 5 for validation tests

Slice tests are faster than full integration tests and test specific layers in isolation.

## Test Coverage

### 1. Validation Tests (`StaffRatingValidationTest`)

Tests server-side validation constraints:
- Valid staff rating passes all validations
- Invalid email format is rejected
- Missing required name field is rejected
- Clarity score below 1 is rejected
- Clarity score above 10 is rejected
- Comment exceeding 500 characters is rejected
- Overall score calculation is accurate

### 2. Controller Tests (`HomeControllerTest`)

Tests web layer using MockMvc:
- GET `/` returns 200 and includes model attributes
- GET `/create` returns form with empty rating object
- POST `/create` with valid data redirects to index
- POST `/create` with invalid data returns form with errors
- GET `/rating/{id}` displays detail page
- GET `/rating/{id}/edit` displays edit form
- POST `/rating/{id}/edit` with valid data redirects to detail page

### 3. Persistence Tests (`StaffRatingRepositoryTest`)

Tests database operations:
- Save and retrieve rating works correctly
- Find all ratings returns complete list
- Delete by ID removes entry from database
- Update rating persists changes

## Running Tests

```bash
mvn test
```

Tests run automatically during the build process.

## Test Results

All tests pass successfully and cover the required CRUD operations with validation.
