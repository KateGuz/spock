package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.SortType;
import ua.spock.spock.entity.User;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.CategoryCacheService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.LotJsonParser;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class LotController {
    @Autowired
    private LotService lotService;
    @Autowired
    private BidService bidService;
    @Autowired
    private CategoryCacheService category;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String getLots(ModelMap model, @RequestParam(value = "sortType", required = false) String sort) {
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        HashMap<Integer, String> timeLeft = new HashMap<>();
        HashMap<Integer, Boolean> isStarted = new HashMap<>();
        HashMap<Integer, Integer> bidCount = new HashMap<>();
        List<Lot> tempLots = lotService.getLots(lotFilter);
        List<Lot> lots = new ArrayList<>();
        for (Lot lot : tempLots) {
            if (isFinished(lot)) {
                lots.add(lot);
                timeLeft.put(lot.getId(), getTimeLeft(lot));
                isStarted.put(lot.getId(), isStarted(lot));
                bidCount.put(lot.getId(), bidService.getBidCountForLot(lot.getId()));
            }
        }
        model.addAttribute("lots", lots);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("isStarted", isStarted);
        model.addAttribute("bidCount", bidCount);
        model.addAttribute("categories", category.getAllCategories());
        return "lots";
    }

    @RequestMapping("/category/{categoryId}")
    public String getLotByCategory(ModelMap model, @RequestParam(value = "sortType", required = false) String sort, @PathVariable Integer categoryId) {
        LotFilter lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        lotFilter.setCategoryId(categoryId);
        HashMap<Integer, String> timeLeft = new HashMap<>();
        HashMap<Integer, Boolean> isStarted = new HashMap<>();
        HashMap<Integer, Integer> bidCount = new HashMap<>();
        List<Lot> tempLots = lotService.getLots(lotFilter);
        List<Lot> lots = new ArrayList<>();
        for (Lot lot : tempLots) {
            if (isFinished(lot)) {
                lots.add(lot);
                timeLeft.put(lot.getId(), getTimeLeft(lot));
                isStarted.put(lot.getId(), isStarted(lot));
                bidCount.put(lot.getId(), bidService.getBidCountForLot(lot.getId()));
            }
        }
        model.addAttribute("lots", lots);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("isStarted", isStarted);
        model.addAttribute("bidCount", bidCount);
        model.addAttribute("categories", category.getAllCategories());
        return "lots";
    }

    @RequestMapping("/lot/{lotId}")
    public String getLotById(ModelMap model, @PathVariable int lotId) {
        Lot lot = lotService.getById(lotId);
        String timeLeft = getTimeLeft(lot);
        String endDate = getEndDate(lot);
        double currentPrice = getCurrentPrice(lot);
        int bidCount = bidService.getBidCountForLot(lotId);
        User user = userService.get(lot.getUser().getId());
        model.addAttribute("user", user);
        model.addAttribute("lot", lot);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentPrice", currentPrice);
        model.addAttribute("bidCount", bidCount);
        return "lot";
    }

    @RequestMapping("/lot")
    public String add(ModelMap model) {
        model.addAttribute("categories", category.getAllCategories());
        return "addLot";
    }

    @RequestMapping(value = "/lot", method = RequestMethod.POST)
    public ResponseEntity addNewLot(@RequestBody String json) {
        Lot lot = LotJsonParser.jsonToLot(json);
        lotService.add(lot);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = " /lot/{lotId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteStudent(@PathVariable("lotId") int id) {
        lotService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
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
        return !interval.isNegative();
    }

    private String getEndDate(Lot lot) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm");
        return lot.getEndDate().format(formatter);
    }

    private double getCurrentPrice(Lot lot) {
        return lot.getMaxBid() == null ? lot.getStartPrice() : lot.getMaxBid().getValue();
    }
}
