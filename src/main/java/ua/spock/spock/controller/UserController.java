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
import ua.spock.spock.controller.util.Util;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.User;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.UserJsonParser;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private Util util;


    @RequestMapping("/user/{id}/edit")
    public String showProfile(ModelMap model, @PathVariable Integer id) {
        model.addAttribute("lots", lotService.getUserLots(id));
        model.addAttribute("user", userService.get(id));
        return "profile";
    }

    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.PUT)
    public ResponseEntity editUser(@PathVariable Integer id, @RequestBody String json) {
        User user = UserJsonParser.jsonToUser(json);
        user.setId(id);
        userService.edit(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}")
    public String editUser(ModelMap model, @PathVariable Integer id) {
        model.addAttribute("user", userService.get(id));
        List<Lot> tempLots = lotService.getUserLots(id);
        model.addAttribute("lots", util.getActualLots(tempLots));
        model.addAttribute("timeLeft",util.getTimeLeft());
        model.addAttribute("isStarted", util.getIsStarted());
        model.addAttribute("bidCount", util.getBidCount());
        return "editUser";
    }
}
