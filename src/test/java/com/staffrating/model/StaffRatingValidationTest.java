package com.staffrating.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StaffRatingValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validRating() {
        StaffRating rating = new StaffRating("Dr. Smith", "smith@uni.edu", RoleType.PROF, 8, 9, 10, "Great");
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidEmail() {
        StaffRating rating = new StaffRating("Dr. Smith", "invalid", RoleType.PROF, 8, 9, 10, null);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
    }

    @Test
    void scoreOutOfRange() {
        StaffRating rating = new StaffRating("Dr. Smith", "smith@uni.edu", RoleType.PROF, 0, 9, 10, null);
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
    }

    @Test
    void commentTooLong() {
        StaffRating rating = new StaffRating("Dr. Smith", "smith@uni.edu", RoleType.PROF, 8, 9, 10, "a".repeat(501));
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
    }
}
