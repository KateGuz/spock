package ua.spock.spock.dto;

import ua.spock.spock.entity.Lot;

import java.util.List;

public class LotDto {
    private Lot lot;
    private String timeLeft;
    private int bidCount;
    private double currentPrice;
    private List<Integer> lotImagesId;

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<Integer> getLotImagesId() {
        return lotImagesId;
    }

    public void setLotImagesId(List<Integer> lotImagesId) {
        this.lotImagesId = lotImagesId;
    }
}
