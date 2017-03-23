package ua.spock.spock.entity;

import ua.spock.spock.dao.LotDao;
import ua.spock.spock.utils.EmailSender;

public class LotBiddingStarter implements Runnable {
    private Lot lot;
    private LotDao lotDao;
    private EmailSender emailSender;

    public void setLotDao(LotDao lotDao) {
        this.lotDao = lotDao;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public LotBiddingStarter(Lot lot) {
        this.lot = lot;
    }

    @Override
    public void run() {
        lotDao.startLotBidding(lot);
        emailSender.sendBiddingStartNotificationEmail(lot);
    }
}
