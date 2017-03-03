package ua.spock.spock.service;

import ua.spock.spock.entity.Report;
import ua.spock.spock.entity.ReportRequest;

public interface ReportService {
    void scheduleReport(ReportRequest reportRequest);
    Report getReport(int reportId);
}
