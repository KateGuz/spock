package ua.spock.spock.entity;

public enum UserType {
    ADMIN("A"), USER("U");

    private String id;

    UserType(String id) {
        this.id = id;
    }

    public static UserType getTypeById(String id) {
        for (UserType userType : values()) {
            if (userType.getId().equals(id)) {
                return userType;
            }
        }

        throw new IllegalArgumentException("No userType found for id = " + id);
    }

    public String getId() {
        return id;
    }
}
