package ua.spock.spock.entity;

public enum ReportOptionType {
    STARTED("started"), ENDED("ended");
    private String id;

    ReportOptionType (String id) {
        this.id = id;
    }

    public static ReportOptionType getRportOptionTypeById(String id) {
        for (ReportOptionType reportOptionType : ReportOptionType.values()) {
            if (reportOptionType.getId().equals(id)) {
                return reportOptionType;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
