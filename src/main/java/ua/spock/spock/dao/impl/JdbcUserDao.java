package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.dao.mapper.UserRowMapper;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;

@Repository
public class JdbcUserDao implements UserDao {

    private final UserRowMapper USER_ROW_MAPPER=new UserRowMapper();
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String addUserSQL;
    @Autowired
    private String getUserSQL;
    @Autowired
    private String getUserByIdSQL;
    @Autowired
    private String editUserSQL;

    @Override
    public void addUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        params.addValue("email", user.getEmail());
        params.addValue("type", UserType.USER.getId());
        namedParameterJdbcTemplate.update(addUserSQL, params);
    }

    @Override
    public User getUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        User userw= namedParameterJdbcTemplate.queryForObject(getUserSQL, params, USER_ROW_MAPPER);
        return userw;
    }

    @Override
    public User getUserById(int id) {
        return namedParameterJdbcTemplate.queryForObject(getUserByIdSQL, new MapSqlParameterSource("id", id), USER_ROW_MAPPER);
    }

    @Override
    public void edit(int id, User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        params.addValue("email", user.getEmail());
        namedParameterJdbcTemplate.update(editUserSQL, params);
    }
}
