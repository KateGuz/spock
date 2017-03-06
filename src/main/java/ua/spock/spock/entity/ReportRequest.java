package ua.spock.spock.entity;


import java.time.LocalDateTime;

public class ReportRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ReportRequestType type;
    private String email;
    private String documentName;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ReportRequestType getType() {
        return type;
    }

    public void setType(ReportRequestType type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", type='" + type + '\'' +
                ", email='" + email + '\'' +
                ", documentName='" + documentName + '\'' +
                '}';
    }
}
