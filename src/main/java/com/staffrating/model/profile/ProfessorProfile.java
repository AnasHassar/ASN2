package com.staffrating.model.profile;

import com.staffrating.model.RoleType;

// Professors implementation
public class ProfessorProfile implements StaffMemberProfile {

    @Override
    public String displayTitle() {
        return "Professor (PhD)";
    }

    @Override
    public RoleType getRoleType() {
        return RoleType.PROF;
    }
}
