package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.entity.*;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.EmailSender;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
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
    @Autowired
    private UserService userService;

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
        int lotId = lotDao.add(lot);
        lot.setId(lotId);
        lot.setUser(userService.get(lot.getUser().getId()));
        scheduleNewLot(lot);
    }

    @Override
    public void edit(Lot lot) {
        Lot beforeEdit = lotDao.getById(lot.getId());
        lotDao.edit(lot);
        if (lot.getStartDate().isBefore(beforeEdit.getStartDate()) ||
                lot.getEndDate().isBefore(beforeEdit.getEndDate())) {
            lot.setUser(userService.get(lot.getUser().getId()));
            scheduleNewLot(lot);
        }
    }

    @Override
    public void updateMaxBidId(Lot lot, int bidId) {
        lotDao.updateMaxBidId(lot, bidId);
    }

    @Override
    public void closeLot(Lot lot) {
        lotDao.closeLot(lot);
        lot = lotDao.getClosedLotForNotification(lot.getId());
        emailSender.sendLotClosingNotificationToBuyer(lot);
        emailSender.sendLotClosingNotificationToOwner(lot);
    }

    @Override
    public List<Lot> getLotsForReport(ReportRequest reportRequest) {
        return lotDao.getLotsForReport(reportRequest);
    }

    @Override
    public Boolean checkIfScheduled(Lot lot) {
        LocalDateTime nextDayStart = LocalDate.now().plusDays(1).atStartOfDay();
        if(lot.getStartDate().isBefore(nextDayStart) || lot.getType() == LotType.IN_PROGRESS) {
            return true;
        } else {
            return false;
        }
    }

    @Scheduled(cron = "30 59 23 ? * *")
    private void dailyScheduleLotsProcessing() {
        LocalDateTime startDate = LocalDate.now().plusDays(1).atStartOfDay();
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
            scheduleLotStart(lot);
        }
        for (Lot lot : startedLotsForProcessing) {
            scheduleLotClose(lot);
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

    private void scheduleNewLot(Lot lot) {
        LocalDateTime nextDayStart = LocalDate.now().plusDays(1).atStartOfDay();
        if (lot.getStartDate().isBefore(nextDayStart) && lot.getType() == LotType.PENDING) {
            scheduleLotStart(lot);
        }
        if (lot.getEndDate().isBefore(nextDayStart)) {
            scheduleLotClose(lot);
        }
    }

    private void scheduleLotStart(Lot lot) {
        LotBiddingStarter starter = new LotBiddingStarter(lot);
        starter.setLotDao(lotDao);
        starter.setEmailSender(emailSender);
        scheduler.schedule(starter, Date.from(lot.getStartDate().atZone(ZoneId.systemDefault()).
                toInstant()));
    }

    private void scheduleLotClose(Lot lot) {
        LotCloser closer = new LotCloser(lot);
        closer.setLotDao(lotDao);
        closer.setEmailSender(emailSender);
        scheduler.schedule(closer, Date.from(lot.getEndDate().atZone(ZoneId.systemDefault()).
                toInstant()));
    }
}
