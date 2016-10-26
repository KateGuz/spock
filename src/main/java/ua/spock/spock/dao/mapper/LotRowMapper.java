package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.spock.spock.dao.mapper.util.LotQueryType;
import ua.spock.spock.entity.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class LotRowMapper implements RowMapper<Lot> {
    private LotQueryType lotQueryType;

    public LotRowMapper(LotQueryType lotQueryType) {
        this.lotQueryType = lotQueryType;
    }

    @Override
    public Lot mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Lot lot = new Lot();
        lot.setId(resultSet.getInt("id"));
        lot.setTitle(resultSet.getString("title"));
        lot.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
        lot.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
        lot.setCurrency(Currency.getCurrencyById(resultSet.getString("currency")));
        lot.setStartPrice(resultSet.getDouble("startPrice"));
        lot.setQuickBuyPrice(resultSet.getDouble("quickBuyPrice"));
        lot.setType(LotType.getTypeById(resultSet.getString("type")));
        int maxBidId = resultSet.getInt("maxBidId");
        if (maxBidId != 0) {
            Bid maxBid = new Bid();
            maxBid.setId(maxBidId);
            maxBid.setValue(resultSet.getDouble("maxBidValue"));
            lot.setMaxBid(maxBid);
        }
        if (lotQueryType.equals(LotQueryType.GET_ONE)) {
            lot.setDescription(resultSet.getString("description"));
            lot.setMinStep(resultSet.getDouble("minStep"));
            Category category = new Category();
            Category parent = new Category();
            category.setId(resultSet.getInt("categoryId"));
            category.setName(resultSet.getString("categoryName"));
            parent.setId(resultSet.getInt("parentCategoryId"));
            parent.setName(resultSet.getString("parentCategoryName"));
            category.setParent(parent);
            lot.setCategory(category);
            User user = new User();
            user.setId(resultSet.getInt("userId"));
            user.setName(resultSet.getString("userName"));
            lot.setUser(user);
        }
        return lot;
    }
}
