package ua.spock.spock.dao;

import ua.spock.spock.entity.User;

public interface UserDao {

    void add(User user);

    User get(User user);

    User get(int id);

    void edit(User user);
}
