package ua.spock.spock.dao.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.SortType;
import ua.spock.spock.filter.LotFilter;

import static ua.spock.spock.entity.SortType.priceAsc;
import static ua.spock.spock.entity.SortType.priceDesc;

@Service
public class QueryGenerator {
    private SqlQueryParameters parameters = new SqlQueryParameters();
    @Autowired
    private String getLotsStatementSQL;
    @Autowired
    private String getLotsByCategoryStatementSQL;

    public SqlQueryParameters generate(LotFilter lotFilter) {
        StringBuilder builder = new StringBuilder();
        builder.append(getLotsStatementSQL);
            if (lotFilter.getCategoryId() != null) {
                builder.append(getLotsByCategoryStatementSQL);
                parameters.setParameters(new MapSqlParameterSource("categoryId", lotFilter.getCategoryId()));
            }
        if ((lotFilter.getSortType() != null)) {
            builder.append(orderByStatement(lotFilter.getSortType()) );
        }
        builder.append(";");
        parameters.setQuery(builder.toString());
        return parameters;
    }

    private String orderByStatement(SortType sorting) {
        String query;
        if (sorting.equals(priceAsc)) {
            query = " ORDER BY l.startPrice OR maxBidValue";
        } else {
            if (sorting.equals(priceDesc)) {
                query = " ORDER BY l.startPrice DESC OR maxBidValue";
            } else {
                query = " ORDER BY l.endDate";
            }
        }
        return query;
    }
}

