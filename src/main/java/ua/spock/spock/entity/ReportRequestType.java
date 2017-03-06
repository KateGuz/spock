package ua.spock.spock.entity;

public enum ReportRequestType {
    STARTED_LOTS("started"), ENDED_LOTS("ended");
    private String id;

    ReportRequestType(String id) {
        this.id = id;
    }

    public static ReportRequestType getReportRequestTypeById(String id) {
        for (ReportRequestType reportRequestType : ReportRequestType.values()) {
            if (reportRequestType.getId().equals(id)) {
                return reportRequestType;
            }
        }
        throw new IllegalArgumentException("No reportType found for id: " + id);
    }

    public String getId() {
        return id;
    }
}
