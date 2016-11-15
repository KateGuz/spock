package ua.spock.spock.entity;

public enum  SortType {
    PRICE_ASC("priceAsc"),PRICE_DESC("priceDesc"),END_SOONEST("endSoonest");
    private String id;

    SortType(String id) {
        this.id = id;
    }

    public static SortType getTypeById(String id) {
        for (SortType sortType : SortType.values()) {
            if (sortType.getId().equals(id)) {
                return sortType;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
