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
        return null;
    }

    public String getId() {
        return id;
    }
}
