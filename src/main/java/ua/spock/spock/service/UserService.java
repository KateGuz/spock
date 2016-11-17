package ua.spock.spock.service;

import ua.spock.spock.entity.User;

public interface UserService {

    void addUser(User user);

    User getUser(User user);

    boolean validate(User user);

    User getUser(int id);
    void edit(int id, User  user);
}
