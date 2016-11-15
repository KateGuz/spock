package ua.spock.spock.dao;

import ua.spock.spock.entity.User;

public interface UserDao {
    User getUserById(int id);
}
