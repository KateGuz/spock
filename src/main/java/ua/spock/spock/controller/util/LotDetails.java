package ua.spock.spock.controller.util;

import ua.spock.spock.entity.Lot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LotDetails {
    private HashMap<Integer, String> timeLeft = new HashMap<>();
    private HashMap<Integer, Boolean> isStarted = new HashMap<>();
    private HashMap<Integer, Boolean> isNotFinished = new HashMap<>();
    private HashMap<Integer, Integer> bidCount = new HashMap<>();
    private HashMap<Integer, Double> currentPrice = new HashMap<>();
    private List<Lot> actualLots = new ArrayList<>();

    public List<Lot> getActualLots() {
        return actualLots;
    }

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

    public HashMap<Integer, Boolean> getIsNotFinished() {
        return isNotFinished;
    }
}
