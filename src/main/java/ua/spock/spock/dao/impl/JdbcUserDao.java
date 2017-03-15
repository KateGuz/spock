package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.dao.mapper.UserRowMapper;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;

@Repository
public class JdbcUserDao implements UserDao {

    private final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
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
    public int add(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        params.addValue("email", user.getEmail());
        params.addValue("type", UserType.USER.getId());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(addUserSQL, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public User get(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        return namedParameterJdbcTemplate.queryForObject(getUserSQL, params, USER_ROW_MAPPER);
    }

    @Override
    public User get(int id) {
        return namedParameterJdbcTemplate.queryForObject(getUserByIdSQL, new MapSqlParameterSource("id", id), USER_ROW_MAPPER);
    }

    @Override
    public void edit(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        params.addValue("email", user.getEmail());
        namedParameterJdbcTemplate.update(editUserSQL, params);
    }
}
