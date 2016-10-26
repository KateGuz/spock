package ua.spock.spock.entity;

public enum UserType {
    U("User"),
    A("Admin");

    private String fullName;
    UserType(String fullName) {
        this.fullName = fullName;
    }

    private String getFullName() {
        return this.fullName;
    }

    public static UserType fromString(String fullName) {
        for (UserType userType : UserType.values()) {
            if (fullName.equalsIgnoreCase(userType.getFullName())) {
                return userType;
            }
        }
        return  null;
    }
}
