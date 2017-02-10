package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.spock.spock.dao.mapper.util.QueryType;
import ua.spock.spock.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LotRowMapper implements RowMapper<Lot> {
    private QueryType queryType;

    public LotRowMapper(QueryType queryType) {
        this.queryType = queryType;
    }

    @Override
    public Lot mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Lot lot = new Lot();
        lot.setId(resultSet.getInt("id"));
        lot.setTitle(resultSet.getString("title"));
        lot.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
        lot.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
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
        if (queryType == QueryType.GET_ONE_LOT) {
            lot.setDescription(resultSet.getString("description"));
            lot.setMinStep(resultSet.getDouble("minStep"));
            lot.setCategory(getCategory(resultSet));
            lot.setUser(getUser(resultSet));
        }
        lot.setImageIds(resultSet.getString("imgIds").split(","));
        lot.setBidCount(resultSet.getInt("bidCount"));
        return lot;
    }

    private Category getCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        Category parent = new Category();
        category.setId(resultSet.getInt("categoryId"));
        category.setName(resultSet.getString("categoryName"));
        parent.setId(resultSet.getInt("parentCategoryId"));
        parent.setName(resultSet.getString("parentCategoryName"));
        category.setParent(parent);
        return category;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("userId"));
        user.setName(resultSet.getString("userName"));
        return user;
    }
}
