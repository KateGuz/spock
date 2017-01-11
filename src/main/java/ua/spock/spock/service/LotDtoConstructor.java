package ua.spock.spock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.LotType;
import ua.spock.spock.entity.dto.LotDto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LotDtoConstructor {
    @Autowired
    private BidService bidService;
    @Autowired
    private CurrencyCacheService currencyCache;

    public List<LotDto> getLotDto(List<Lot> lots, String currency) {
        List<LotDto> list = new ArrayList<>();
        for (Lot lot : lots) {
            if (lot.getType() != LotType.CLOSED) {
                list.add(construct(lot, currency));
            }
        }
        return list;
    }

    public List<LotDto> getAllUserLotDto(List<Lot> lots, String currency) {
        List<LotDto> list = new ArrayList<>();
        for (Lot lot : lots) {
            list.add(construct(lot, currency));
        }
        return list;
    }

    public LotDto construct(Lot lot, String currency) {
        LotDto lotDto = new LotDto();
        lotDto.setBidCount(bidService.getBidCountForLot(lot.getId()));
        lotDto.setTimeLeft(getTimeLeft(lot));
        lotDto.setCurrentPrice(getCurrentPrice(lot));
        updateLotPrice(lot, currency);
        lotDto.setLot(lot);
        return lotDto;
    }

    public double getCurrentPrice(Lot lot) {
        return lot.getMaxBid() == null ? lot.getStartPrice() : lot.getMaxBid().getValue();
    }

    private void updateLotPrice(Lot lot, String currency) {
        double rate = 1;
        if (currency.equals("USD")) {
            rate = currencyCache.getRates().get("USD");
        } else {
            if (currency.equals("EUR")) {
                rate = currencyCache.getRates().get("EUR");
            }
        }
        BigDecimal minStep = new BigDecimal(lot.getMinStep());
        minStep = minStep.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
        lot.setMinStep(minStep.doubleValue());
        if (lot.getMaxBid() != null) {
            BigDecimal maxBidValue = new BigDecimal(lot.getMaxBid().getValue());
            maxBidValue = maxBidValue.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
            lot.getMaxBid().setValue(maxBidValue.doubleValue());
        }
        BigDecimal startPrice = new BigDecimal(lot.getStartPrice());
        startPrice = startPrice.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
        lot.setStartPrice(startPrice.doubleValue());
        BigDecimal quickBuyPrice = new BigDecimal(lot.getQuickBuyPrice());
        quickBuyPrice = quickBuyPrice.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
        lot.setQuickBuyPrice(quickBuyPrice.doubleValue());

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
}
