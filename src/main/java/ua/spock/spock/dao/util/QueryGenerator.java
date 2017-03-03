package ua.spock.spock.dao.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.entity.ReportRequestType;
import ua.spock.spock.entity.SortType;
import ua.spock.spock.filter.LotFilter;

import static ua.spock.spock.entity.SortType.*;

@Service
public class QueryGenerator {
    private SqlQueryParameters parameters = new SqlQueryParameters();
    @Autowired
    private String getLotsStatementSQL;
    @Autowired
    private String getLotsByCategoryStatementSQL;
    @Autowired
    private String getLotCountStatementSQL;
    @Autowired
    private String getLotsForReportStatementSQL;

    public SqlQueryParameters generate(LotFilter lotFilter, int lotsPerPage) {
        MapSqlParameterSource paramsMap = new MapSqlParameterSource();
        paramsMap.addValue("offset", (lotFilter.getPage() - 1) * lotsPerPage);
        paramsMap.addValue("lotsPerPage", lotsPerPage);
        StringBuilder query = new StringBuilder();
        query.append(getLotsStatementSQL);
        Integer categoryId = lotFilter.getCategoryId();
        if (categoryId != null) {
            query.append(getLotsByCategoryStatementSQL);
            paramsMap.addValue("categoryId", categoryId);
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append("l.type != 'C'");
        SortType sortType = lotFilter.getSortType();
        if (sortType != null) {
            query.append(getOrderByStatement(sortType));
        }
        query.append(" LIMIT :offset, :lotsPerPage;");
        parameters.setParameters(paramsMap);
        parameters.setQuery(query.toString());
        return parameters;
    }

    public SqlQueryParameters generateCount(LotFilter lotFilter) {
        MapSqlParameterSource paramsMap = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder();
        query.append(getLotCountStatementSQL);
        if (lotFilter.getCategoryId() != null) {
            query.append(getLotsByCategoryStatementSQL);
            paramsMap.addValue("categoryId", lotFilter.getCategoryId());
        } else {
            query.append(" WHERE l.type != 'C'");
        }
        query.append(";");
        parameters.setParameters(paramsMap);
        parameters.setQuery(query.toString());
        return parameters;
    }

    public String generateReportQuery(ReportRequest reportRequest) {
        StringBuilder query = new StringBuilder();
        query.append(getLotsForReportStatementSQL);
        query.append("WHERE (");
        if (reportRequest.getType() == ReportRequestType.STARTED_LOTS) {
            query.append("l.startDate>'").append(reportRequest.getStartDate()).append("' AND ").
                    append("l.startDate<'").append(reportRequest.getEndDate()).append("');");
        } else {
            query.append("l.endDate>'").append(reportRequest.getStartDate()).append("' AND ").
                    append("l.endDate<'").append(reportRequest.getEndDate()).append("');");
        }
        return query.toString();
    }

    private String getOrderByStatement(SortType sorting) {
        StringBuilder query = new StringBuilder(" ORDER BY");
        if (sorting == PRICE_ASC) {
            query.append(" CASE WHEN l.maxBidId IS NULL THEN l.startPrice ELSE b.bid END");
        } else {
            if (sorting.equals(PRICE_DESC)) {
                query.append(" CASE WHEN l.maxBidId IS NULL THEN l.startPrice ELSE b.bid END DESC ");
            } else {
                query.append(" l.endDate");
            }
        }
        return query.toString();
    }
}

