package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.spock.spock.entity.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class LotRowMapper implements RowMapper<Lot> {
    @Override
    public Lot mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();

        Lot lot = new Lot();

        for (int i = 1; i < numberOfColumns + 1; i++) {
            String nthColumnName = rsMetaData.getColumnName(i);
            if("description".equals(nthColumnName)) {
                lot.setDescription(resultSet.getString("description"));
            }
            else if("categoryId".equals(nthColumnName)) {
                Category category = new Category();
                Category parent = new Category();
                category.setId(resultSet.getInt("categoryId"));
                category.setName(resultSet.getString("categoryName"));
                parent.setId(resultSet.getInt("parentId"));
                parent.setName(resultSet.getString("parentName"));
                category.setParent(parent);
                lot.setCategory(category);
            }
            else if("userName".equals(nthColumnName)) {
                User user = new User();
                user.setName(resultSet.getString("userName"));
            }
            else if("minStep".equals(nthColumnName)) {
                lot.setMinStep(resultSet.getDouble("minStep"));
            }
            else if("startDate".equals(nthColumnName)) {
                lot.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
            }
        }

        lot.setId(resultSet.getInt("id"));
        lot.setTitle(resultSet.getString("title"));
        lot.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
        lot.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        lot.setStartPrice(resultSet.getDouble("startPrice"));
        lot.setQuickBuyPrice(resultSet.getDouble("quickBuyPrice"));
        lot.setType(LotType.valueOf(resultSet.getString("lotType")));

        if (resultSet.getInt("maxBidId") != 0) {
            Bid maxBid = new Bid();
            maxBid.setId(resultSet.getInt("maxBidId"));
            maxBid.setValue(resultSet.getInt("maxBidValue"));
            lot.setMaxBid(maxBid);
        }

        return lot;
    }
}
