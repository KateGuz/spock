package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.spock.spock.controller.util.LotDetails;
import ua.spock.spock.controller.util.LotDetailsWrapper;
import ua.spock.spock.controller.util.ModelMapAttributesWrapper;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.UserJsonParser;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private LotDetailsWrapper lotDetailsWrapper;
    @Autowired
    private ModelMapAttributesWrapper modelMapAttributesWrapper;

    @RequestMapping("/user/{id}/edit")
    public String showProfile(ModelMap model, @PathVariable Integer id, @RequestParam(value = "currency", required = false) String currency, HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == id) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                if (currency != null) {
                    session.setAttribute("currency", currency);
                }
                if (session.getAttribute("currency") == null) {
                    session.setAttribute("currency", "UAH");
                }
                model.addAttribute("lots", lotService.getUserLots(id));
                model.addAttribute("user", userService.get(id));
                model.addAttribute("currency", session.getAttribute("currency"));
                return "profile";
            } else {
                return "lots";
            }
        } else {
            return "lots";
        }
    }

    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.PUT)
    public ResponseEntity editUser(@PathVariable Integer id, @RequestBody String json, HttpSession session) {
        User user = UserJsonParser.jsonToUser(json);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == id) || (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN))) {
                user.setId(id);
                userService.edit(user);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/user/{id}")
    public String editUser(ModelMap model, @PathVariable Integer id, @RequestParam(value = "currency", required = false) String currency, HttpSession session) {
        if (currency != null) {
            session.setAttribute("currency", currency);
        }
        if (session.getAttribute("currency") == null) {
            session.setAttribute("currency", "UAH");
        }
        model.addAttribute("user", userService.get(id));
        List<Lot> tempLots = lotService.getUserLots(id);
        LotDetails lotDetails = lotDetailsWrapper.prepareDataForUser(tempLots, session);
        modelMapAttributesWrapper.fillLotAtributes(model, lotDetails);
        model.addAttribute("currency", session.getAttribute("currency"));
        return "editUser";
    }
}
