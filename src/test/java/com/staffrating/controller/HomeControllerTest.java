package com.staffrating.controller;

import com.staffrating.model.RoleType;
import com.staffrating.model.StaffRating;
import com.staffrating.repository.StaffRatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffRatingRepository repository;

    @Test
    void indexPage() throws Exception {
        when(repository.findAll()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void createForm() throws Exception {
        mockMvc.perform(get("/create")).andExpect(status().isOk());
    }

    @Test
    void createSuccess() throws Exception {
        mockMvc.perform(post("/create")
                .param("name", "Dr. Smith")
                .param("email", "smith@uni.edu")
                .param("roleType", "PROF")
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeableScore", "10"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void detailPage() throws Exception {
        StaffRating rating = new StaffRating("Dr. Smith", "smith@uni.edu", RoleType.PROF, 8, 9, 10, null);
        rating.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(rating));
        mockMvc.perform(get("/rating/1")).andExpect(status().isOk());
    }

    @Test
    void editForm() throws Exception {
        StaffRating rating = new StaffRating("Dr. Smith", "smith@uni.edu", RoleType.PROF, 8, 9, 10, null);
        rating.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(rating));
        mockMvc.perform(get("/rating/1/edit")).andExpect(status().isOk());
    }

    @Test
    void editSuccess() throws Exception {
        mockMvc.perform(post("/rating/1/edit")
                .param("name", "Dr. Smith")
                .param("email", "smith@uni.edu")
                .param("roleType", "PROF")
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeableScore", "10"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createWithoutRoleShowsValidationError() throws Exception {
        // Test edge case: submitting form without selecting a role
        mockMvc.perform(post("/create")
                .param("name", "Dr. Smith")
                .param("email", "smith@uni.edu")
                .param("roleType", "") // Empty role - the edge case
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeableScore", "10"))
                .andExpect(status().isOk()) // Should stay on form page
                .andExpect(model().attributeHasFieldErrors("rating", "roleType")); // Should have validation error
    }

    @Test
    void editWithoutRoleShowsValidationError() throws Exception {
        // Test edge case: submitting edit form without selecting a role
        mockMvc.perform(post("/rating/1/edit")
                .param("name", "Dr. Smith")
                .param("email", "smith@uni.edu")
                .param("roleType", "") // Empty role - the edge case
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeableScore", "10"))
                .andExpect(status().isOk()) // Should stay on form page
                .andExpect(model().attributeHasFieldErrors("rating", "roleType")); // Should have validation error
    }
}
