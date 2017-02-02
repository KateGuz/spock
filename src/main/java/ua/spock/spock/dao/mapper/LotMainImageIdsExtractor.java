package ua.spock.spock.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LotMainImageIdsExtractor implements ResultSetExtractor<Map<Integer, Integer>>{
    @Override
    public Map<Integer, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Integer> lotImageIds = new HashMap<>();
        while (rs.next()) {
            lotImageIds.put(rs.getInt("lotId"), rs.getInt("id"));
        }
        return lotImageIds;
    }
}
