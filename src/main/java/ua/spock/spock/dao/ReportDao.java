package ua.spock.spock.dao;

import ua.spock.spock.entity.Report;

public interface ReportDao {
    int saveReport(Report report);

    Report getReport(int reportId);

    void cleanReports();
}
