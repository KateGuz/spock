package ua.spock.spock.service;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.SortType;


import java.util.List;

public interface LotService {
    List<Lot> getAll(SortType sortType);
    Lot getById(int lotId);
}
