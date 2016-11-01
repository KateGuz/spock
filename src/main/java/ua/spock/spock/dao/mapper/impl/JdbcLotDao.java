package ua.spock.spock.dao.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.mapper.LotDao;
import ua.spock.spock.dao.mapper.LotRowMapper;
import ua.spock.spock.dao.mapper.util.QueryType;
import ua.spock.spock.entity.Lot;

@Repository
public class JdbcLotDao implements LotDao {
    private final LotRowMapper lotRowMapper = new LotRowMapper(QueryType.GET_ONE_LOT);
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getLotByIdSQL;

    @Override
    public Lot getLotById(int lotId) {
        return namedParameterJdbcTemplate.queryForObject(getLotByIdSQL, new MapSqlParameterSource("lotId", lotId), lotRowMapper);
    }
}
