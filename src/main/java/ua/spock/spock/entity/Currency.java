package ua.spock.spock.entity;

public enum Currency {
    UAH("Ukrainian hryvnia"),
    USD("US dollar"),
    EUR("Euro");
    String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public static Currency fromString(String fullName) {
        for (Currency currency :Currency.values()) {
            if (fullName.equalsIgnoreCase(currency.getFullName())) {
                return currency;
            }
        }
        return  null;
    }
}
