package com.staffrating.model.profile;

import com.staffrating.model.RoleType;

// General interface for staff members
public interface StaffMemberProfile {

    /**
     * Returns the display title for this staff member role
     * 
     * @return formatted title string
     */
    String displayTitle();

    /**
     * Returns the role type this profile represents
     * 
     * @return the RoleType enum value
     */
    RoleType getRoleType();
}
