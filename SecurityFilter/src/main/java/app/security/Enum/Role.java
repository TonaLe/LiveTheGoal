package app.security.Enum;

/**
 * The enum Role.
 */
public enum Role {
    /**
     * User role.
     */
    USER("User"),
    /**
     * Admin role.
     */
    ADMIN("Admin");

    /**
     * The Role.
     */
    private final String role;

    /**
     * Instantiates a new Role.
     *
     * @param role the role
     */
    Role(String role) {
        this.role = role;
    };

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return role;
    }
}
