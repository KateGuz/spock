package ua.spock.spock.entity;

public enum LotType {
    PENDING("P"), IN_PROGRESS("I"), CLOSED("C");
    private String id;

    LotType(String id) {
        this.id = id;
    }

    public static LotType getTypeById(String id) {
        for (LotType lotType : LotType.values()) {
            if (lotType.getId().equals(id)) {
                return lotType;
            }
        }
        throw new IllegalArgumentException("No lotType found for id = " + id);
    }

    public String getId() {
        return id;
    }
}
