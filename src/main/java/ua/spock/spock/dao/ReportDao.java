package ua.spock.spock.dao;

import java.io.InputStream;

public interface ReportDao {
    int saveReport(InputStream report);

    byte[] getReport(int reportId);

    void cleanReports();
}
