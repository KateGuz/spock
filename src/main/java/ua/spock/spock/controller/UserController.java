package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.spock.spock.entity.User;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.JsonParser;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;

    @RequestMapping("/user/{id}")
    public String showProfile(ModelMap model, @PathVariable Integer id) {
        model.addAttribute("lots", lotService.getUserLots(id));
        model.addAttribute("user", userService.getUser(id));
        return "profile";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity editUser(@PathVariable Integer id, @RequestBody String json) {
        User user = JsonParser.jsonToUser(json);
        userService.edit(id, user);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/user/{id}/edit")
    public String editUser(ModelMap model,@PathVariable Integer id) {
        model.addAttribute("user", userService.getUser(id));
        return "editUser";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody String json, HttpSession session) {
        User user = JsonParser.jsonToUser(json);
        if (userService.validate(user)) {
            userService.addUser(user);
            session.setAttribute("loggedUser", user);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity logIn(@RequestBody String json, HttpSession session) {
        User tempUser = JsonParser.jsonToUser(json);
        User user = userService.getUser(tempUser);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loggedUser");
        return "redirect:/";
    }
}
