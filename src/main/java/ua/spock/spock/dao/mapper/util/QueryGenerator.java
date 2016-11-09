package ua.spock.spock.dao.mapper.util;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.SortType;
import ua.spock.spock.entity.SqlQueryParameters;

@Service
public class QueryGenerator {
    private SqlQueryParameters parameters = new SqlQueryParameters();
    private String query;
    @Autowired
    private String getAllLotsSQL;
    @Autowired
    private String getLotsByCategorySQL;

    public SqlQueryParameters getParameters() {
        return parameters;
    }

    public void generate(SortType sortType) {
        if (sortType.getCategoryId() == null) {
            query = getAllLotsSQL;
        } else {
            if (sortType.getCategoryId() != null) {
                query = getLotsByCategorySQL;
                parameters.setMap(new MapSqlParameterSource("categoryId", sortType.getCategoryId()));
            }
        }
        if ((sortType.getSortType() != null)) {
            query = query + addSorting(sortType.getSortType());
        }
        parameters.setQuery(query);
    }

    private String addSorting(String sorting) {
        if (sorting.equals("ASC")) {
            query = " ORDER BY l.startPrice";
        } else {
            if (sorting.equals("DESC")) {
                query = " ORDER BY l.startPrice DESC";
            } else {
                query = " ORDER BY l.endDate";
            }
        }
        return query;
    }
}

