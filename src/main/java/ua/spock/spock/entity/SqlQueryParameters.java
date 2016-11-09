package ua.spock.spock.entity;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class SqlQueryParameters {
    private MapSqlParameterSource map;
    private String query;

    public MapSqlParameterSource getMap() {
        return map;
    }

    public void setMap(MapSqlParameterSource map) {
        this.map = map;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
