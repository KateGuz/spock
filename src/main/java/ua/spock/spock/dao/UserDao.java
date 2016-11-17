package ua.spock.spock.dao;

import ua.spock.spock.entity.User;

public interface UserDao {

    void addUser(User user);

    User getUser(User user);

    User getUser(int id);

    void edit(int id, User user);
}
