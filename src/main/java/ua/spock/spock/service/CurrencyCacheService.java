package ua.spock.spock.service;

import java.util.HashMap;

public interface CurrencyCacheService {
    HashMap<String, Double> getRates();
}
