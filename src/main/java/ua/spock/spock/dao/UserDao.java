package ua.spock.spock.dao;

import ua.spock.spock.entity.User;

public interface UserDao {

    void addUser(User user);

    User getUser(User user);

    User getUserById(int id);

    void edit(int id, User user);
}
