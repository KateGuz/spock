package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.spock.spock.entity.Bid;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.LotType;
import ua.spock.spock.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LotForNotificationRowMapper implements RowMapper<Lot> {
    @Override
    public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lot lot = new Lot();
        lot.setId(rs.getInt("id"));
        lot.setTitle(rs.getString("title"));
        lot.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
        lot.setEndDate(rs.getTimestamp("endDate").toLocalDateTime());
        lot.setType(LotType.getTypeById(rs.getString("type")));
        User user = new User();
        user.setId(rs.getInt("userId"));
        user.setName(rs.getString("userName"));
        user.setEmail(rs.getString("userEmail"));
        lot.setUser(user);
        int maxBidId = rs.getInt("maxBidId");
        if (maxBidId != 0) {
            Bid maxBid = new Bid();
            maxBid.setId(maxBidId);
            maxBid.setValue(rs.getDouble("maxBidValue"));
            User bidUser = new User();
            bidUser.setId(rs.getInt("maxBidUserId"));
            bidUser.setName(rs.getString("maxBidUserName"));
            bidUser.setEmail(rs.getString("maxBidUserEmail"));
            maxBid.setUser(bidUser);
            lot.setMaxBid(maxBid);
        }
        return lot;
    }
}
