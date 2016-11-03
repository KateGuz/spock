package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.BidService;
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

    @RequestMapping("/lot/{lotId}")
    public String getLotById(ModelMap model, @PathVariable int lotId) {
        Lot lot = lotService.getLotById(lotId);
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
        }
        else if (interval.toHours() > 0){
            Duration minLeft = interval.minusHours(interval.toHours());
            timeLeft = String.valueOf(interval.toHours()) + (interval.toHours() > 1 ? " hrs " : " hr ") +
                    String.valueOf(minLeft.toMinutes()) +" min";
        } else {
            timeLeft = String.valueOf(interval.toMinutes()) +" min";
        }

        return timeLeft;
    }

    private String getEndDate(Lot lot) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm");;
        String endDate = lot.getEndDate().format(formatter);
        return endDate;
    }

    private double getCurrentPrice (Lot lot) {
        return lot.getMaxBid() == null ? lot.getStartPrice() : lot.getMaxBid().getValue();
    }
}
