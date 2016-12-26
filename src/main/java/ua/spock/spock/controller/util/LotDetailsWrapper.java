package ua.spock.spock.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.LotType;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.CurrencyCasheService;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LotDetailsWrapper {
    @Autowired
    private BidService bidService;
    @Autowired
    private CurrencyCasheService currencyCashe;

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

    public LotDetails prepareData(List<Lot> tempLots, HttpSession session) {
        LotDetails details = new LotDetails();
        for (Lot lot : tempLots) {
            if (isNotFinished(lot) && isNotClosed(lot) && isStarted(lot)) {
                prepareOneLot(details, lot, session);
            }
        }
        return details;
    }

    public LotDetails prepareDataForUser(List<Lot> tempLots, HttpSession session) {
        LotDetails details = new LotDetails();
        for (Lot lot : tempLots) {
            prepareOneLot(details, lot, session);
        }
        return details;
    }

    public boolean isStarted(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getStartDate());
        return interval.isNegative();
    }

    public boolean isNotFinished(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getEndDate());
        return !interval.isNegative();
    }

    private boolean isNotClosed(Lot lot) {
        return lot.getType() != LotType.CLOSED;
    }

    private void prepareOneLot(LotDetails details, Lot lot, HttpSession session) {
        details.getActualLots().add(lot);
        details.getTimeLeft().put(lot.getId(), getTimeLeft(lot));
        details.getIsStarted().put(lot.getId(), isStarted(lot));
        details.getIsNotFinished().put(lot.getId(), isNotFinished(lot));
        details.getBidCount().put(lot.getId(), bidService.getBidCountForLot(lot.getId()));
        details.getCurrentPrice().put(lot.getId(), getCurrentPrice(lot));
        lotCurrency(lot, session);
    }

    public Lot lotCurrency(Lot lot, HttpSession session) {
        double currencyValue;
        if (session.getAttribute("currency").equals("UAH")) {
            return lot;
        } else {
            if (session.getAttribute("currency").equals("USD")) {
                currencyValue = currencyCashe.getCurrencyValue().get("USD");

            } else {
                currencyValue = currencyCashe.getCurrencyValue().get("EUR");
            }
            double minStep = lot.getMinStep() / currencyValue;
            lot.setMinStep(Double.parseDouble(String.format("%.2f", minStep)));
            if (lot.getMaxBid() != null) {
                double maxBidValue = lot.getMaxBid().getValue() / currencyValue;
                lot.getMaxBid().setValue(Double.parseDouble(String.format("%.2f", maxBidValue)));
            }
            double startPrice = lot.getStartPrice() / currencyValue;
            lot.setStartPrice(Double.parseDouble(String.format("%.2f", startPrice)));
            double quickBuyPrice = lot.getQuickBuyPrice() / currencyValue;

            lot.setQuickBuyPrice(Double.parseDouble(String.format("%.2f", quickBuyPrice)));
            return lot;
        }
    }
}
