package ua.spock.spock.service;

import ua.spock.spock.entity.ReportRequest;

import java.io.InputStream;

public interface ReportService {
    void scheduleReport(ReportRequest reportRequest);
    int saveReport(InputStream report);
    byte[] getReport(int reportId);
}
