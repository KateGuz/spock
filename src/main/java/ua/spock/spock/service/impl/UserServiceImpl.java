package ua.spock.spock.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.UserDao;
import ua.spock.spock.entity.User;
import ua.spock.spock.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
}
