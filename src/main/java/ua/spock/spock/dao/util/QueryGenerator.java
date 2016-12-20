package ua.spock.spock.dao.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
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

    public SqlQueryParameters generate(LotFilter lotFilter, int page, int lotsPerPage) {
        MapSqlParameterSource paramsMap = new MapSqlParameterSource();
        paramsMap.addValue("offset", (page - 1) * lotsPerPage);
        paramsMap.addValue("lotsPerPage", lotsPerPage);
        StringBuilder query = new StringBuilder();
        query.append(getLotsStatementSQL);
        if (lotFilter.getCategoryId() != null) {
            query.append(getLotsByCategoryStatementSQL);
            paramsMap.addValue("categoryId", lotFilter.getCategoryId());
        } else {
            query.append(" WHERE l.type = 'I'");
        }
        if (lotFilter.getSortType() != null) {
            query.append(getOrderByStatement(lotFilter.getSortType()));
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
            query.append(" WHERE l.type = 'I'");
        }
        query.append(";");
        parameters.setParameters(paramsMap);
        parameters.setQuery(query.toString());
        return parameters;
    }

    private String getOrderByStatement(SortType sorting) {
        StringBuilder query = new StringBuilder(" ORDER BY");
        if (sorting == PRICE_ASC) {
            query.append(" l.startPrice ");
        } else {
            if (sorting.equals(PRICE_DESC)) {
                query.append(" l.startPrice DESC ");
            } else {
                query.append(" l.endDate");
            }
        }
        return query.toString();
    }
}

