package ua.spock.spock.entity;

public enum LotType {
    P("Pending"),
    I("In progress"),
    C("Closed");
    String  fullName;

    LotType(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public static LotType fromString(String fullName) {
        for (LotType lotType : LotType.values()) {
            if (fullName.equalsIgnoreCase(lotType.getFullName())) {
                return lotType;
            }
        }
        return  null;
    }
}
