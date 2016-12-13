package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.BidDao;
import ua.spock.spock.entity.Bid;

@Repository
public class JdbcBidDao implements BidDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getBidCountByLotIdSQL;
    @Autowired
    private String placeBidSQL;

    @Override
    public int getBidCountForLot(int lotId) {
       return  namedParameterJdbcTemplate.queryForObject(getBidCountByLotIdSQL, new MapSqlParameterSource("lotId", lotId), Integer.class);
    }

    @Override
    public int placeBid(Bid bid) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bid", bid.getValue());
        params.addValue("lotId", bid.getLot().getId());
        params.addValue("userId", bid.getUser().getId());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(placeBidSQL, params, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
