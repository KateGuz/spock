package ua.spock.spock.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.BidService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class LotDetailsWrapper {
    @Autowired
    private BidService bidService;
    private HashMap<Integer, String> timeLeft = new HashMap<>();
    private HashMap<Integer, Boolean> isStarted = new HashMap<>();
    private HashMap<Integer, Integer> bidCount = new HashMap<>();
    private HashMap<Integer, Double> currentPrice = new HashMap<>();

    public HashMap<Integer, Double> getCurrentPrice() {
        return currentPrice;
    }

    public HashMap<Integer, String> getTimeLeft() {
        return timeLeft;
    }

    public HashMap<Integer, Boolean> getIsStarted() {
        return isStarted;
    }

    public HashMap<Integer, Integer> getBidCount() {
        return bidCount;
    }

    public String getTimeLeft(Lot lot) {
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

    public String getEndDate(Lot lot) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm");
        return lot.getEndDate().format(formatter);
    }

    public double getCurrentPrice(Lot lot) {
        return lot.getMaxBid() == null ? lot.getStartPrice() : lot.getMaxBid().getValue();
    }

    public List<Lot> prepareData(List<Lot> tempLots) {
        List<Lot> lots = new ArrayList<>();
        for (Lot lot : tempLots) {
            if (isNotFinished(lot)) {
                lots.add(lot);
                timeLeft.put(lot.getId(), getTimeLeft(lot));
                isStarted.put(lot.getId(), isStarted(lot));
                bidCount.put(lot.getId(), bidService.getBidCountForLot(lot.getId()));
                currentPrice.put(lot.getId(), getCurrentPrice(lot));
            }
        }
        return lots;
    }

    private boolean isStarted(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getStartDate());
        return interval.isNegative();
    }

    private boolean isNotFinished(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getEndDate());
        return !interval.isNegative();
    }
}