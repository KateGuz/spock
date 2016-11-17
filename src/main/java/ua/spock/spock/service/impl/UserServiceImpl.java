package ua.spock.spock.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;
import ua.spock.spock.service.UserService;
import ua.spock.spock.service.utils.EmailValidator;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addUser(User user) {
        user.setType(UserType.USER);
        userDao.addUser(user);
    }

    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }

    @Override
    public boolean validate(User user) {
        EmailValidator emailValidator = new EmailValidator();
        return ((user.getName() != null) && (user.getPassword() != null) && (user.getEmail() != null) && (user.getName().length() > 1) && (emailValidator.validate(user.getEmail())));
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);

    }

    @Override
    public void edit(int id, User user) {
        userDao.edit(id,user);
    }
}
