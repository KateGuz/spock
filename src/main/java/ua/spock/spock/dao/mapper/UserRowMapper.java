package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setType(UserType.getTypeById(resultSet.getString("type")));
        return user;
    }
}
