package ua.spock.spock.dao;

import ua.spock.spock.entity.User;

public interface UserDao {

    int add(User user);

    User get(User user);

    User get(int id);

    void edit(User user);
}
