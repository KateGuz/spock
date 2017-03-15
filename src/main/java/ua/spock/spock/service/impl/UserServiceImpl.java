package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.entity.User;
import ua.spock.spock.service.UserService;
import ua.spock.spock.service.utils.EmailValidator;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void add(User user) {
        int userId = userDao.add(user);
        user.setId(userId);
    }

    @Override
    public User get(User user) {
        return userDao.get(user);
    }

    @Override
    public boolean validate(User user) {
        EmailValidator emailValidator = new EmailValidator();
        return ((user.getName() != null) && (user.getPassword() != null) && (user.getEmail() != null) && (user.getName().length() > 1) && (emailValidator.validate(user.getEmail())));
    }

    @Override
    public User get(int id) {
        return userDao.get(id);

    }

    @Override
    public void edit(User user) {
        userDao.edit(user);
    }
}
