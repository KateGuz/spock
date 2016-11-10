package ua.spock.spock.service;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.filter.LotFilter;


import java.util.List;

public interface LotService {
    List<Lot> getAll(LotFilter lotFilter);
    Lot getById(int lotId);
}
