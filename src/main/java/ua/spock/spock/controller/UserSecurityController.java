package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.spock.spock.entity.User;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.UserJsonParser;

import javax.servlet.http.HttpSession;

@Controller
public class UserSecurityController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody String json, HttpSession session) {
        User user = UserJsonParser.jsonToUser(json);
        if (userService.validate(user)) {
            userService.add(user);
            session.setAttribute("loggedUser", user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity logIn(@RequestBody String json, HttpSession session) {
        User tempUser = UserJsonParser.jsonToUser(json);
        User user = userService.get(tempUser);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loggedUser");
        return "redirect:/";
    }
}
