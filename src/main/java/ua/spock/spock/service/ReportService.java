package ua.spock.spock.service;

import ua.spock.spock.entity.ReportOption;

public interface ReportService {
    void scheduleReport(ReportOption reportOption);
}
