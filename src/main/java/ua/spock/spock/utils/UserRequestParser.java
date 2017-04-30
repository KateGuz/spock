package ua.spock.spock.utils;

import ua.spock.spock.entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserRequestParser {
    public static User requestToUser(HttpServletRequest httpServletRequest) {
        User user = new User();
        String name = httpServletRequest.getParameter("name");
        user.setName(name);
        String password = httpServletRequest.getParameter("password");
        user.setPassword(password);
        String email = httpServletRequest.getParameter("email");
        user.setEmail(email);
        return user;
    }
}
