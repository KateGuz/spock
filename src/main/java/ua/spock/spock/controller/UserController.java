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
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.User;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.UserJsonParser;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private BidService bidService;

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
        HashMap<Integer, String> timeLeft = new HashMap<>();
        HashMap<Integer, Boolean> isStarted = new HashMap<>();
        HashMap<Integer, Boolean> isFinished = new HashMap<>();
        HashMap<Integer, Integer> bidCount = new HashMap<>();
        List<Lot> tempLots = lotService.getUserLots(id);
        List<Lot> lots = new ArrayList<>();
        for (Lot lot : tempLots) {
            isFinished.put(lot.getId(), isFinished(lot));
            timeLeft.put(lot.getId(), getTimeLeft(lot));
            isStarted.put(lot.getId(), isStarted(lot));
            bidCount.put(lot.getId(), bidService.getBidCountForLot(lot.getId()));
        }
        model.addAttribute("lots", lots);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("isStarted", isStarted);
        model.addAttribute("isFinished", isFinished);
        model.addAttribute("bidCount", bidCount);
        model.addAttribute("lots", lotService.getUserLots(id));
        return "editUser";
    }

    private String getTimeLeft(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getEndDate());
        String timeLeft;
        if (interval.toDays() > 1) {
            timeLeft = String.valueOf(interval.toDays()) + (interval.toDays() > 1 ? " days" : " day");

        } else if (interval.toDays() > 0) {
            Duration hrsLeft = interval.minusDays(interval.toDays());
            timeLeft = "1 day " + hrsLeft.toHours() + (hrsLeft.toHours() > 1 ? " hrs" : " hr");
        } else if (interval.toHours() > 0) {
            Duration minLeft = interval.minusHours(interval.toHours());
            timeLeft = String.valueOf(interval.toHours()) + (interval.toHours() > 1 ? " hrs " : " hr ") +
                    String.valueOf(minLeft.toMinutes()) + " min";
        } else {
            timeLeft = String.valueOf(interval.toMinutes()) + " min";
        }
        return timeLeft;
    }

    private boolean isStarted(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getStartDate());
        return interval.isNegative();
    }

    private boolean isFinished(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getEndDate());
        return interval.isNegative();
    }


}
