package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.LotBiddingStarter;
import ua.spock.spock.entity.LotCloser;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.LotService;
import ua.spock.spock.utils.EmailSender;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class LotServiceImpl implements LotService {
    @Autowired
    private LotDao lotDao;
    @Autowired
    private TaskScheduler scheduler;
    @Autowired
    private EmailSender emailSender;

    @Override
    public List<Lot> getLots(LotFilter lotFilter) {
        return lotDao.get(lotFilter);
    }

    @Override
    public int getPageCount(LotFilter lotFilter) {
        return lotDao.getPageCount(lotFilter);
    }

    public Lot getById(int lotId) {
        return lotDao.getById(lotId);
    }

    @Override
    public List<Lot> getUserLots(int userId) {
        return lotDao.getByUser(userId);
    }

    @Override
    public void delete(int id) {
        lotDao.delete(id);
    }

    @Override
    public void add(Lot lot) {
        lotDao.add(lot);
    }

    @Override
    public void edit(Lot lot) {
        lotDao.edit(lot);
    }

    @Override
    public void updateMaxBidId(Lot lot, int bidId) {
        lotDao.updateMaxBidId(lot, bidId);
    }

    @Override
    public void closeLot(Lot lot) {
        lotDao.closeLot(lot);
    }

    @Override
    public List<Lot> getLotsForReport(ReportRequest reportRequest) {
        return lotDao.getLotsForReport(reportRequest);
    }

    @Scheduled(cron = "0 59 23 ? * *")
    private void dailyScheduleLotsProcessing() {
        LocalDateTime startDate = LocalDateTime.now().toLocalDate().atStartOfDay().plusDays(1);
        LocalDateTime endDate = startDate.plusDays(1);
        scheduleLotsProcessing(startDate, endDate);
    }

    @PostConstruct
    private void initialScheduleLotsProcessing() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.toLocalDate().atStartOfDay().plusDays(1);
        scheduleLotsProcessing(startDate, endDate);
    }

    private void scheduleLotsProcessing(LocalDateTime startDate, LocalDateTime endDate) {
        List<Lot> pendingLotsForProcessing = lotDao.getPendingLotsForProcessing(startDate, endDate);
        List<Lot> startedLotsForProcessing = lotDao.getStartedLotsForProcessing(startDate, endDate);
        for (Lot lot : pendingLotsForProcessing) {
            LotBiddingStarter starter = new LotBiddingStarter(lot);
            starter.setLotDao(lotDao);
            starter.setEmailSender(emailSender);
            scheduler.schedule(starter, Date.from(lot.getStartDate().atZone(ZoneId.systemDefault()).
                    toInstant()));

        }
        for (Lot lot : startedLotsForProcessing) {
            LotCloser closer = new LotCloser(lot);
            closer.setLotDao(lotDao);
            closer.setEmailSender(emailSender);
            scheduler.schedule(closer, Date.from(lot.getEndDate().atZone(ZoneId.systemDefault()).
                    toInstant()));
        }
    }

    @PostConstruct
    private void processOverdueLots() {
        List<Lot> overduePendingLotsForProcessing = lotDao.getOverduePendingLotsForProcessing(LocalDateTime.now());
        for (Lot lot : overduePendingLotsForProcessing) {
            lotDao.startLotBidding(lot);
            emailSender.sendBiddingStartNotificationEmail(lot);
        }
        List<Lot> overdueStartedLotsForProcessing = lotDao.getOverdueStartedLotsForProcessing(LocalDateTime.now());
        for (Lot lot : overdueStartedLotsForProcessing) {
            lotDao.closeLot(lot);
            lot = lotDao.getClosedLotForNotification(lot.getId());
            emailSender.sendLotClosingNotificationToOwner(lot);
            if (lot.getMaxBid() != null) {
                emailSender.sendLotClosingNotificationToBuyer(lot);
            }
        }
    }
}
