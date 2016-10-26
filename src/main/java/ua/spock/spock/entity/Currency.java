package ua.spock.spock.entity;

public enum Currency {
    USD("USD"), EUR("EUR"), UAH("UAH");
    private String id;

    Currency(String id) {
        this.id = id;
    }

    public static Currency getCurrencyById(String id){
        for (Currency currency : Currency.values()) {
            if(currency.getId().equals(id)){
                return currency;
            }
        }
        throw new IllegalArgumentException("No currency found for id = " + id);
    }

    public String getId() {
        return id;
    }
}
