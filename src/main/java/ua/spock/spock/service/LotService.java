package ua.spock.spock.service;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.filter.LotFilter;


import java.util.List;

public interface LotService {
    List<Lot> getLots(LotFilter lotFilter, int page, int lotsPerPage);
    int getLotCount(LotFilter lotFilter);
    Lot getById(int lotId);
    List<Lot> getUserLots(int userId);
    void delete(int id);
    void add(Lot lot);
    void edit(Lot lot);
    void updateMaxBidId(Lot lot, int bidId);
    void closeLot(Lot lot);
}
