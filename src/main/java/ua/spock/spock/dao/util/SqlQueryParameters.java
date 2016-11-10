package ua.spock.spock.dao.util;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class SqlQueryParameters {
    private MapSqlParameterSource parameters;
    private String query;

    public MapSqlParameterSource getParameters() {
        return parameters;
    }

    public void setParameters(MapSqlParameterSource map) {
        this.parameters = map;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
