package ua.spock.spock.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.dao.mapper.UserRowMapper;
import ua.spock.spock.entity.User;
@Repository
public class JdbcUserDao implements UserDao {
    private final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

   @Autowired
   private String getUserByIdSQL;


    @Override
    public User getUserById(int id) {
        return namedParameterJdbcTemplate.queryForObject(getUserByIdSQL, new MapSqlParameterSource("id", id), USER_ROW_MAPPER);
    }
}
