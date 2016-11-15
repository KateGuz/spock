package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;


@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    private LotFilter lotFilter;

    @RequestMapping(" /user/{id}")
    public String showProfile(ModelMap model, @PathVariable Integer id) {
        lotFilter= new LotFilter();
        lotFilter.setUserId(id);
        model.addAttribute("lots", lotService.getAll(lotFilter));
        model.addAttribute("user", userService.getUserById(id));
        System.out.println(userService.getUserById(id));
        return "profile";
    }
}
