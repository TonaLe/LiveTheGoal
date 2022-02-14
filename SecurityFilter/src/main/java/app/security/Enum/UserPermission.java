package app.security.Enum;

public enum UserPermission {
    SUBJECT_READ("subject:read"),
    SUBJECT_WRITE("subject:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
