package com.staffrating.repository;

import com.staffrating.model.RoleType;
import com.staffrating.model.StaffRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository repository;

    @Test
    void saveAndFind() {
        StaffRating rating = new StaffRating("Dr. Smith", "smith@uni.edu", RoleType.PROF, 8, 9, 10, null);
        StaffRating saved = repository.save(rating);

        assertNotNull(saved.getId());
        Optional<StaffRating> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Dr. Smith", found.get().getName());
    }

    @Test
    void delete() {
        StaffRating rating = new StaffRating("Dr. Smith", "smith@uni.edu", RoleType.PROF, 8, 9, 10, null);
        StaffRating saved = repository.save(rating);

        repository.deleteById(saved.getId());

        Optional<StaffRating> found = repository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}
