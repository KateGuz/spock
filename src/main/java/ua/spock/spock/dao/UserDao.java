package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.User;

import java.util.List;

public interface UserDao {

    int add(User user);

    User get(User user);

    User get(int id);

    void edit(User user);

    boolean checkIfSubscribed(User user, Lot lot);

    void subscribe(User user, Lot lot);

    void unSubscribe(User user, Lot lot);

    List<User> getUsersForNotification(Lot lot);
}
