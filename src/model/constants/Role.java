package model.constants;

public enum Role {
    MANAGER,CHEF, CUSTOMER;

    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        return CUSTOMER;
    }
}
