package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.dao.mapper.LotRowMapper;
import ua.spock.spock.dao.mapper.util.QueryType;
import ua.spock.spock.dao.util.QueryGenerator;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.LotType;
import ua.spock.spock.filter.LotFilter;

import java.util.List;

@Repository
public class JdbcLotDao implements LotDao {
    private final LotRowMapper ALL_LOTS_ROW_MAPPER = new LotRowMapper(QueryType.GET_ALL_LOTS);
    private final LotRowMapper LOT_ROW_MAPPER = new LotRowMapper(QueryType.GET_ONE_LOT);
    @Autowired
    private QueryGenerator queryGenerator;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getLotByIdSQL;
    @Autowired
    private String getLotsByUserIdSQL;
    @Autowired
    private String deleteLotSQL;
    @Autowired
    private String addLotSQL;
    @Autowired
    private String editLotSQL;
    @Autowired
    private String updateMaxBidIdSQL;
    @Autowired
    private String closeLotSQL;
    @Autowired
    private String getCountByUserSQL;


    @Override
    public Lot getById(int lotId) {
        return namedParameterJdbcTemplate.queryForObject(getLotByIdSQL, new MapSqlParameterSource("lotId", lotId), LOT_ROW_MAPPER);
    }

    @Override
    public List<Lot> getByUser(int userId, int page, int lotsPerPage) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("offset", (page - 1) * lotsPerPage);
        params.addValue("lotsPerPage", lotsPerPage);
        return namedParameterJdbcTemplate.query(getLotsByUserIdSQL, params, ALL_LOTS_ROW_MAPPER);
    }

    @Override
    public int getLotCountByUser(int userId) {
        return namedParameterJdbcTemplate.queryForObject(getCountByUserSQL, new MapSqlParameterSource("userId", userId), Integer.class);
    }

    @Override
    public void add(Lot lot) {
        MapSqlParameterSource params = fillParams(lot);
        namedParameterJdbcTemplate.update(addLotSQL, params);

    }

    @Override
    public void delete(int id) {
        namedParameterJdbcTemplate.update(deleteLotSQL, new MapSqlParameterSource("lotId",id));
    }

    @Override
    public void edit(Lot lot) {
        MapSqlParameterSource params = fillParams(lot);
        params.addValue("id", lot.getId());
        namedParameterJdbcTemplate.update(editLotSQL, params);
    }

    @Override
    public List<Lot> get(LotFilter lotFilter) {
        return namedParameterJdbcTemplate.query(queryGenerator.generate(lotFilter).getQuery(), queryGenerator.generate(lotFilter).getParameters(), ALL_LOTS_ROW_MAPPER);
    }

    @Override
    public void updateMaxBidId(Lot lot, int bidId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", lot.getId());
        params.addValue("bidId", bidId);
        namedParameterJdbcTemplate.update(updateMaxBidIdSQL, params);
    }

    @Override
    public void closeLot(Lot lot) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", lot.getId());
        params.addValue("type", LotType.CLOSED.getId());
        namedParameterJdbcTemplate.update(closeLotSQL, params);
    }

    private MapSqlParameterSource fillParams(Lot lot) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", lot.getTitle());
        params.addValue("description", lot.getDescription());
        params.addValue("categoryId", lot.getCategory().getId());
        params.addValue("startDate", lot.getStartDate());
        params.addValue("endDate", lot.getEndDate());
        params.addValue("startPrice", lot.getStartPrice());
        params.addValue("minStep", lot.getMinStep());
        params.addValue("quickBuyPrice", lot.getQuickBuyPrice());
        params.addValue("userId", lot.getUser().getId());
        params.addValue("type", lot.getType().getId());
        return params;
    }
}

