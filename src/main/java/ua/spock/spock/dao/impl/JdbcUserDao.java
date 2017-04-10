package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.dao.mapper.UserForNotificationRowMapper;
import ua.spock.spock.dao.mapper.UserRowMapper;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;

import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {

    private final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final UserForNotificationRowMapper USER_FOR_NOTIFICATION_ROW_MAPPER = new UserForNotificationRowMapper();
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
    @Autowired
    private String checkIfSubscribedSQL;
    @Autowired
    private String subscribeSQL;
    @Autowired
    private String unSubscribeSQL;
    @Autowired
    private String getUsersForNotificationSQL;

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
        params.addValue("email", user.getEmail());
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

    @Override
    public boolean checkIfSubscribed(User user, Lot lot) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        params.addValue("lotId", lot.getId());
        return namedParameterJdbcTemplate.queryForObject(checkIfSubscribedSQL, params, Integer.class) == 1;
    }

    @Override
    public void subscribe(User user, Lot lot) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        params.addValue("lotId", lot.getId());
        namedParameterJdbcTemplate.update(subscribeSQL, params);
    }

    @Override
    public void unSubscribe(User user, Lot lot) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());
        params.addValue("lotId", lot.getId());
        namedParameterJdbcTemplate.update(unSubscribeSQL, params);
    }

    @Override
    public List<User> getUsersForNotification(Lot lot) {
        MapSqlParameterSource params = new MapSqlParameterSource("lotId", lot.getId());
        return namedParameterJdbcTemplate.query(getUsersForNotificationSQL, params, USER_FOR_NOTIFICATION_ROW_MAPPER);
    }
}
