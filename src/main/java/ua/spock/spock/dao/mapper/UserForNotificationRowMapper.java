package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.spock.spock.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserForNotificationRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setEmail(rs.getString("email"));
        return user;
    }
}
