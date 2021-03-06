package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.filter.LotFilter;

import java.util.List;

public interface LotDao {
    List<Lot> get(LotFilter lotFilter);
    int getPageCount(LotFilter lotFilter);
    Lot getById(int lotId);
    List<Lot> getByUser(int userId);
    void add(Lot lot);
    void delete(int id);
    void edit(Lot lot);
    void updateMaxBidId(Lot lot, int bidId);
    void closeLot(Lot lot);
    List<Lot> getLotsForReport(ReportRequest reportRequest);
}
