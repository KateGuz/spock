package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.filter.LotFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface LotDao {
    List<Lot> get(LotFilter lotFilter);
    int getPageCount(LotFilter lotFilter);
    Lot getById(int lotId);
    List<Lot> getByUser(int userId);
    int add(Lot lot);
    void delete(int id);
    void edit(Lot lot);
    void updateMaxBidId(Lot lot, int bidId);
    void closeLot(Lot lot);
    List<Lot> getLotsForReport(ReportRequest reportRequest);
    List<Lot> getPendingLotsForProcessing(LocalDateTime startPeriod, LocalDateTime endPeriod);
    List<Lot> getOverduePendingLotsForProcessing(LocalDateTime now);
    List<Lot> getStartedLotsForProcessing(LocalDateTime startPeriod, LocalDateTime endPeriod);
    List<Lot> getOverdueStartedLotsForProcessing(LocalDateTime now);
    Lot getClosedLotForNotification(int lotId);
    void startLotBidding(Lot lot);
}
