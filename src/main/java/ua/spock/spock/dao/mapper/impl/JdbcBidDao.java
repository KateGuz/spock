package ua.spock.spock.dao.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.mapper.BidDao;

@Repository
public class JdbcBidDao implements BidDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getBidCountByLotIdSQL;

    @Override
    public int getBidCountForLot(int lotId) {
       return  namedParameterJdbcTemplate.queryForObject(getBidCountByLotIdSQL, new MapSqlParameterSource("lotId", lotId), Integer.class);
    }
}
