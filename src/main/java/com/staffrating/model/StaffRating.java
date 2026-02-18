package com.staffrating.model;

import com.staffrating.model.profile.StaffMemberProfile;
import com.staffrating.model.profile.TAProfile;
import com.staffrating.model.profile.ProfessorProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "staff_ratings")
public class StaffRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Role type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @NotNull(message = "Clarity rating is required")
    @Min(value = 1, message = "Clarity rating must be at least 1")
    @Max(value = 10, message = "Clarity rating must be at most 10")
    @Column(nullable = false)
    private Integer clarity;

    @NotNull(message = "Niceness rating is required")
    @Min(value = 1, message = "Niceness rating must be at least 1")
    @Max(value = 10, message = "Niceness rating must be at most 10")
    @Column(nullable = false)
    private Integer niceness;

    @NotNull(message = "Knowledge rating is required")
    @Min(value = 1, message = "Knowledge rating must be at least 1")
    @Max(value = 10, message = "Knowledge rating must be at most 10")
    @Column(nullable = false)
    private Integer knowledgeableScore;

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    @Column(length = 500)
    private String comment;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructors
    public StaffRating() {
    }

    public StaffRating(String name, String email, RoleType roleType, Integer clarity,
            Integer niceness, Integer knowledgeableScore, String comment) {
        this.name = name;
        this.email = email;
        this.roleType = roleType;
        this.clarity = clarity;
        this.niceness = niceness;
        this.knowledgeableScore = knowledgeableScore;
        this.comment = comment;
    }

    // Business logic methods

    /**
     * Calculates the overall score as the average of all three ratings
     * 
     * @return average score rounded to one decimal place
     */
    public double getOverallScore() {
        if (clarity == null || niceness == null || knowledgeableScore == null) {
            return 0.0;
        }
        return Math.round((clarity + niceness + knowledgeableScore) / 3.0 * 10.0) / 10.0;
    }

    /**
     * Gets the display title using polymorphic behavior (only for TA and PROF)
     * 
     * @return formatted title string
     */
    public String getDisplayTitle() {
        if (roleType == null) {
            return "Unknown Role";
        }
        StaffMemberProfile profile;
        if (roleType == RoleType.TA) {
            profile = new TAProfile();
        } else if (roleType == RoleType.PROF) {
            profile = new ProfessorProfile();
        } else {
            return roleType.getDisplayName();
        }
        return profile.displayTitle();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getNiceness() {
        return niceness;
    }

    public void setNiceness(Integer niceness) {
        this.niceness = niceness;
    }

    public Integer getKnowledgeableScore() {
        return knowledgeableScore;
    }

    public void setKnowledgeableScore(Integer knowledgeableScore) {
        this.knowledgeableScore = knowledgeableScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
