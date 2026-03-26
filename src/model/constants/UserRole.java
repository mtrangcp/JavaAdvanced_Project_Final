package model.constants;

public enum UserRole {
    MANAGER,CHEF, CUSTOMER;

    public static UserRole fromString(String role) {
        for (UserRole r : UserRole.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        return CUSTOMER;
    }
}
