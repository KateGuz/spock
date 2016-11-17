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


@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    private LotFilter lotFilter;

    @RequestMapping(" /user/{id}")
    public String showProfile(ModelMap model, @PathVariable Integer id) {
        lotFilter = new LotFilter();
        lotFilter.setUserId(id);
        model.addAttribute("lots", lotService.getAll(lotFilter));
        model.addAttribute("user", userService.getUser(id));
        return "profile";
    }

    @RequestMapping(value = " /user/{id}", method = RequestMethod.PUT)
    public ResponseEntity editProfile(@PathVariable Integer id, @RequestBody String json) {
        User user = JsonParser.jsonToUser(json);
        userService.edit(id, user);
        return new ResponseEntity(HttpStatus.OK);
    }


}
