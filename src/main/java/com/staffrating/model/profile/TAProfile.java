package com.staffrating.model.profile;

import com.staffrating.model.RoleType;

// Concrete implementation
public class TAProfile implements StaffMemberProfile {

    @Override
    public String displayTitle() {
        return "Teaching Assistant (TA)";
    }

    @Override
    public RoleType getRoleType() {
        return RoleType.TA;
    }
}
