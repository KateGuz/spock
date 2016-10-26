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
        lot.setId(resultSet.getInt("id"));
        lot.setTitle(resultSet.getString("title"));

        if(columnExists(rsMetaData, numberOfColumns, "description")) {
            lot.setDescription(resultSet.getString("description"));
        }

        Category category = new Category();
        category.setName(resultSet.getString("categoryName"));
        lot.setCategory(category);

        if(columnExists(rsMetaData, numberOfColumns, "userName")) {
            User user = new User();
            user.setName(resultSet.getString("userName"));
        }

        lot.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
        lot.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
        lot.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        lot.setStartPrice(resultSet.getDouble("startPrice"));
        lot.setMinStep(resultSet.getDouble("minStep"));
        lot.setQuickBuyPrice(resultSet.getDouble("quickBuyPrice"));
        lot.setType(LotType.valueOf(resultSet.getString("lotType")));


        if (resultSet.getInt("maxBidId") != 0) {
            Bid maxBid = new Bid();
            maxBid.setId(resultSet.getInt("maxBidValue"));
            maxBid.setValue(resultSet.getInt("maxBidValue"));
            lot.setMaxBid(maxBid);
        }

        return lot;
    }

    private boolean columnExists(ResultSetMetaData rsMetaData, int numberOfColumns, String columnName) throws SQLException {
        for (int i = 1; i < numberOfColumns + 1; i++) {
            String nthColumnName = rsMetaData.getColumnName(i);
            if (nthColumnName.equals(columnName)) {
                return true;
            }
        }
        return false;
    }
}
