package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.dao.mapper.UserRowMapper;
import ua.spock.spock.entity.User;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcUserDao implements UserDao {
    @Autowired
    UserRowMapper userRowMapper;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String addUserSQL;
    @Autowired
    private String getUserSQL;
    @Autowired
    private String getUserByIdSQL;

    @Override
    public void addUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        params.addValue("email", user.getEmail());
        params.addValue("type", user.getType().getId());
        namedParameterJdbcTemplate.update(addUserSQL, params);
    }

    @Override
    public User getUser(User user) {
        Map<String, String> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("password", user.getPassword());
        return namedParameterJdbcTemplate.queryForObject(getUserSQL, new MapSqlParameterSource(map), userRowMapper);
    }

    @Override
    public User getUser(int id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(getUserByIdSQL, new MapSqlParameterSource(map), userRowMapper);
    }
}
