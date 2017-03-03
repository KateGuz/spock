package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.ReportDao;
import ua.spock.spock.entity.Report;

@Repository
public class JdbcReportDao implements ReportDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String saveReportSQL;
    @Autowired
    private String getReportSQL;
    @Autowired
    private String cleanReportsSQL;

    @Override
    public int saveReport(Report report) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("report", report.getInputStream());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(saveReportSQL, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Report getReport(int reportId) {
        MapSqlParameterSource params = new MapSqlParameterSource("reportId", reportId);
        Report report = new Report();
        report.setBody(namedParameterJdbcTemplate.queryForObject(getReportSQL, params, byte[].class));
        return report;
    }

    @Override
    public void cleanReports() {
        namedParameterJdbcTemplate.update(cleanReportsSQL, new MapSqlParameterSource());
    }
}
