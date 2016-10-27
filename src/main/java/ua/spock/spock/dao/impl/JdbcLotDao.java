package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.dao.mapper.LotRowMapper;
import ua.spock.spock.entity.Lot;

import java.util.List;

import static ua.spock.spock.dao.mapper.util.QueryType.GET_ALL_LOTS;

@Repository
public class JdbcLotDao implements LotDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getAllLotsSQL;
    @Autowired
    private String getLotsByCategorySQL;

    @Override
    public List<Lot> getAll() {
        return namedParameterJdbcTemplate.query(getAllLotsSQL, new LotRowMapper(GET_ALL_LOTS));
    }

    @Override
    public List<Lot> getLotsByCategory(int categoryId) {
        return namedParameterJdbcTemplate.query(getLotsByCategorySQL, new MapSqlParameterSource("categoryId", categoryId), new LotRowMapper(GET_ALL_LOTS));
    }
}

