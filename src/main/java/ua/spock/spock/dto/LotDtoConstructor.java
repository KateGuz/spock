package ua.spock.spock.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.spock.spock.entity.Currency;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.ImageService;
import ua.spock.spock.service.cache.CurrencyCache;

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
    private CurrencyCache currencyCache;
    @Autowired
    private ImageService imageService;

    public List<LotDto> constructListOfLots(List<Lot> lots, Currency currency) {
        List<LotDto> list = new ArrayList<>();
        for (Lot lot : lots) {
            list.add(constructOneLot(lot, currency));
        }
        return list;
    }

    public LotDto constructOneLot(Lot lot, Currency currency) {
        LotDto lotDto = new LotDto();
        lotDto.setBidCount(bidService.getBidCountForLot(lot.getId()));
        lotDto.setTimeLeft(getTimeLeft(lot));
        lotDto.setLotImagesId(imageService.getLotImagesId(lot.getId()));
        updateLotPrices(lotDto, lot, currency);
        lotDto.setLot(lot);
        return lotDto;
    }

    private void updateLotPrices(LotDto lotDto, Lot lot, Currency currency) {
        double rate = 1;
        if (currency == Currency.USD) {
            rate = currencyCache.getRates().get("USD");
        } else if (currency == Currency.EUR) {
            rate = currencyCache.getRates().get("EUR");
        }
        if (rate != 1) {
            BigDecimal startPrice = new BigDecimal(lot.getStartPrice());
            startPrice = startPrice.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
            double startPriceValue = startPrice.doubleValue();
            lot.setStartPrice(startPriceValue);

            double quickBuyValue = lot.getQuickBuyPrice();
            if (quickBuyValue != 0) {
                BigDecimal quickBuyPrice = new BigDecimal(quickBuyValue);
                quickBuyPrice = quickBuyPrice.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
                lot.setQuickBuyPrice(quickBuyPrice.doubleValue());
            }

            double minStepOld = lot.getMinStep();
            if (minStepOld != 0) {
                BigDecimal minStep = new BigDecimal(minStepOld);
                minStep = minStep.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
                lot.setMinStep(minStep.doubleValue());
            }

            if (lot.getMaxBid() != null) {
                BigDecimal maxBid = new BigDecimal(lot.getMaxBid().getValue());
                maxBid = maxBid.divide(BigDecimal.valueOf(rate), 2, BigDecimal.ROUND_CEILING);
                double maxBidValue = maxBid.doubleValue();
                lot.getMaxBid().setValue(maxBidValue);
                lotDto.setCurrentPrice(maxBidValue);
            } else {
                lotDto.setCurrentPrice(startPriceValue);
            }
        } else {
            if (lot.getMaxBid() != null) {
                lotDto.setCurrentPrice(lot.getMaxBid().getValue());
            } else {
                lotDto.setCurrentPrice(lot.getStartPrice());
            }
        }
    }

    private String getTimeLeft(Lot lot) {
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getEndDate());
        String timeLeft;
        if (interval.toDays() > 1) {
            timeLeft = String.valueOf(interval.toDays()) + " дн";
        } else if (interval.toDays() > 0) {
            Duration hrsLeft = interval.minusDays(interval.toDays());
            timeLeft = "1 дн " + hrsLeft.toHours() + " час";
        } else if (interval.toHours() > 0) {
            Duration minLeft = interval.minusHours(interval.toHours());
            timeLeft = String.valueOf(interval.toHours()) + " час " +
                    String.valueOf(minLeft.toMinutes()) + " мин";
        } else {
            timeLeft = String.valueOf(interval.toMinutes()) + " мин";
        }
        return timeLeft;
    }
}
