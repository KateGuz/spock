package ua.spock.spock.controller.util;

import ua.spock.spock.entity.Lot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Util {
    public   String getTimeLeft(Lot lot) {
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

    public boolean isStarted(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getStartDate());
        return interval.isNegative();
    }

    public boolean isFinished(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getEndDate());
        return !interval.isNegative();
    }

    public String getEndDate(Lot lot) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm");
        return lot.getEndDate().format(formatter);
    }

    public double getCurrentPrice(Lot lot) {
        return lot.getMaxBid() == null ? lot.getStartPrice() : lot.getMaxBid().getValue();
    }
}
