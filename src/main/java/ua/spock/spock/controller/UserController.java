package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.spock.spock.entity.Currency;
import ua.spock.spock.entity.Image;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;
import ua.spock.spock.service.ImageService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.dto.LotDtoConstructor;
import ua.spock.spock.utils.UserJsonParser;
import ua.spock.spock.utils.UserRequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private LotDtoConstructor lotDtoConstructor;

    @RequestMapping("/user/{id}/edit")
    public String showProfile(ModelMap model, @PathVariable Integer id, @RequestParam(value = "currency", required = false) String currency, HttpSession session) {
        if (session.getAttribute("loggedUser") != null) {
            System.out.println(((User) session.getAttribute("loggedUser")).getId());
            if ((((User) session.getAttribute("loggedUser")).getId() == id) || (((User) session.getAttribute("loggedUser")).getType() == UserType.ADMIN)) {
                if (currency != null) {
                    session.setAttribute("currency", currency);
                }
                if (session.getAttribute("currency") == null) {
                    session.setAttribute("currency", "UAH");
                }
                model.addAttribute("user", userService.get(id));
                model.addAttribute("currency", session.getAttribute("currency"));
                return "editUser";
            }
        }
        return "error";
    }

    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.POST)
    public ResponseEntity editUser(@PathVariable Integer id, @RequestParam(name = "userImage", required = false) MultipartFile userImage, HttpServletRequest httpServletRequest, HttpSession session) throws IOException {
        User user = UserRequestParser.requestToUser(httpServletRequest);
        if (session.getAttribute("loggedUser") != null) {
            if ((((User) session.getAttribute("loggedUser")).getId() == id) || (((User) session.getAttribute("loggedUser")).getType() == UserType.ADMIN)) {
                user.setId(id);
                if (userImage != null) {
                    Image image = new Image();
                    try {
                        image.setBytes(userImage.getBytes());
                        image.setId(id);
                        imageService.saveUserImage(image);
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                }
                userService.edit(user);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/user/{id}")
    public String editUser(ModelMap model, @PathVariable Integer id, @RequestParam(value = "currency", required = false) String currency, HttpSession session) {
        if (currency != null) {
            session.setAttribute("currency", currency);
        }
        if (session.getAttribute("currency") == null) {
            session.setAttribute("currency", "UAH");
        }
        currency = (String) session.getAttribute("currency");
        model.addAttribute("user", userService.get(id));
        model.addAttribute("lots", lotDtoConstructor.constructListOfLots(lotService.getUserLots(id), Currency.valueOf(currency)));
        model.addAttribute("currency", currency);
        return "profile";
    }
}
