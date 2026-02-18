package com.staffrating.model;

public enum RoleType {
    TA("Teaching Assistant"),
    PROF("Professor"),
    INSTRUCTOR("Instructor"),
    STAFF("Staff");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
