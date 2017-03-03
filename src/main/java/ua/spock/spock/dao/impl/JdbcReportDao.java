package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.ReportDao;

import java.io.InputStream;

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
    public int saveReport(InputStream report) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("report", report);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(saveReportSQL, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public byte[] getReport(int reportId) {
        MapSqlParameterSource params = new MapSqlParameterSource("reportId", reportId);
        return namedParameterJdbcTemplate.queryForObject(getReportSQL, params, byte[].class);
    }

    @Override
    public void cleanReports() {
        namedParameterJdbcTemplate.update(cleanReportsSQL, new MapSqlParameterSource());
    }
}
