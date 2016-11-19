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

    public SqlQueryParameters generate(LotFilter lotFilter) {
        StringBuilder query = new StringBuilder();
        query.append(getLotsStatementSQL);
        if (lotFilter.getCategoryId() != null) {
            query.append(getLotsByCategoryStatementSQL);
            parameters.setParameters(new MapSqlParameterSource("categoryId", lotFilter.getCategoryId()));
        }
        if (lotFilter.getSortType() != null) {
            query.append(getOrderByStatement(lotFilter.getSortType()));
        }
        query.append(";");
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

