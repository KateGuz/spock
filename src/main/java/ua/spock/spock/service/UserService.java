package ua.spock.spock.service;

import ua.spock.spock.entity.User;

public interface UserService {

    void add(User user);

    User get(User user);

    boolean validate(User user);

    User get(int id);
    void edit(User  user);
}
