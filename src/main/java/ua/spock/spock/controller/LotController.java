package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.spock.spock.entity.Lot;
import org.springframework.web.bind.annotation.RequestParam;
import ua.spock.spock.entity.SortType;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.CategoryCacheService;
import ua.spock.spock.service.LotService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class LotController {
    @Autowired
    private LotService lotService;
    @Autowired
    private BidService bidService;
    @Autowired
    private CategoryCacheService category;

    private LotFilter lotFilter;


    @RequestMapping("/")
    public String getLots(ModelMap model, @RequestParam(value = "sortType", required = false) String sort) {
        lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        model.addAttribute("lots", lotService.getLots(lotFilter));
        model.addAttribute("categories", category.getAllCategories());
        return "lots";
    }

    @RequestMapping("/category/{categoryId}")
    public String getLotByCategory(ModelMap model, @RequestParam(value = "sortType", required = false) String sort, @PathVariable Integer categoryId) {
        lotFilter = new LotFilter();
        lotFilter.setSortType(SortType.getTypeById(sort));
        lotFilter.setCategoryId(categoryId);
        model.addAttribute("lots", lotService.getLots(lotFilter));
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
        model.addAttribute("lot", lot);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentPrice", currentPrice);
        model.addAttribute("bidCount", bidCount);
        return "lot";
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

    private String getEndDate(Lot lot) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm");
        return lot.getEndDate().format(formatter);
    }

    private double getCurrentPrice(Lot lot) {
        return lot.getMaxBid() == null ? lot.getStartPrice() : lot.getMaxBid().getValue();
    }
}
