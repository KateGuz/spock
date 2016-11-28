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
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.UserJsonParser;

@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;

    @RequestMapping("/user/{id}")
    public String showProfile(ModelMap model, @PathVariable Integer id) {
        model.addAttribute("lots", lotService.getUserLots(id));
        model.addAttribute("user", userService.get(id));
        return "profile";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity editUser(@PathVariable Integer id, @RequestBody String json) {
        User user = UserJsonParser.jsonToUser(json);
        user.setId(id);
        userService.edit(user);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/user/{id}/edit")
    public String editUser(ModelMap model,@PathVariable Integer id) {
        model.addAttribute("user", userService.get(id));
        model.addAttribute("lots", lotService.getUserLots(id));
        return "editUser";
    }
}
