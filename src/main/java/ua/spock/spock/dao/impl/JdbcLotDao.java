package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.ImageDao;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.dao.mapper.LotRowMapper;
import ua.spock.spock.dao.mapper.util.QueryType;
import ua.spock.spock.dao.util.QueryGenerator;
import ua.spock.spock.dao.util.SqlQueryParameters;
import ua.spock.spock.entity.Image;
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
    private ImageDao imageDao;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private String getLotByIdSQL;
    @Autowired
    private String getLotsByUserIdSQL;
    @Autowired
    private String deleteLotSQL;
    @Autowired
    private String deleteLotForeignKeySQL;
    @Autowired
    private String editLotSQL;
    @Autowired
    private String updateMaxBidIdSQL;
    @Autowired
    private String closeLotSQL;
    private final int LOTS_PER_PAGE = 9;

    @Override
    public Lot getById(int lotId) {
        return namedParameterJdbcTemplate.queryForObject(getLotByIdSQL, new MapSqlParameterSource("lotId", lotId), LOT_ROW_MAPPER);
    }

    @Override
    public List<Lot> getByUser(int userId) {

        return namedParameterJdbcTemplate.query(getLotsByUserIdSQL, new MapSqlParameterSource("userId", userId), ALL_LOTS_ROW_MAPPER);
    }

    @Override
    public void add(Lot lot) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("lot").usingGeneratedKeyColumns("id");
        Number lotId = simpleJdbcInsert.executeAndReturnKey(fillParams(lot));
        Image image = lot.getPrimaryImage();
        image.setId(lotId.intValue());
        imageDao.saveLotImage(image);
        image = lot.getSecondaryImage();
        image.setId(lotId.intValue());
        imageDao.saveLotImage(image);
    }

    @Override
    public void delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource("lotId", id);
        namedParameterJdbcTemplate.update(deleteLotForeignKeySQL,map);
        namedParameterJdbcTemplate.update(deleteLotSQL,map);
    }

    @Override
    public void edit(Lot lot) {
        MapSqlParameterSource params = fillParams(lot);
        params.addValue("id", lot.getId());
        namedParameterJdbcTemplate.update(editLotSQL, params);
    }

    @Override
    public List<Lot> get(LotFilter lotFilter) {
        SqlQueryParameters sqlQueryParameters = queryGenerator.generate(lotFilter, LOTS_PER_PAGE);
        String query = sqlQueryParameters.getQuery();
        MapSqlParameterSource params = sqlQueryParameters.getParameters();
        return namedParameterJdbcTemplate.query(query, params, ALL_LOTS_ROW_MAPPER);
    }

    @Override
    public int getPageCount(LotFilter lotFilter) {
        SqlQueryParameters sqlQueryParameters = queryGenerator.generateCount(lotFilter);
        String query = sqlQueryParameters.getQuery();
        MapSqlParameterSource params = sqlQueryParameters.getParameters();
        int lotCount = namedParameterJdbcTemplate.queryForObject(query, params, Integer.class);
        return (int) Math.ceil(lotCount * 1.0 / LOTS_PER_PAGE);

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

