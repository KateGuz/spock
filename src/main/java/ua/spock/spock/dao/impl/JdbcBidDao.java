package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.BidDao;
import ua.spock.spock.entity.Bid;

@Repository
public class JdbcBidDao implements BidDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private String getBidCountByLotIdSQL;

    @Override
    public int getBidCountForLot(int lotId) {
        return namedParameterJdbcTemplate.queryForObject(getBidCountByLotIdSQL, new MapSqlParameterSource("lotId", lotId), Integer.class);
    }

    @Override
    public int add(Bid bid) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("bid").usingGeneratedKeyColumns("id");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bid", bid.getValue());
        params.addValue("lotId", bid.getLot().getId());
        params.addValue("userId", bid.getUser().getId());
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }
}
