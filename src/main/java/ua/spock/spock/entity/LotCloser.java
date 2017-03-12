package ua.spock.spock.entity;

import ua.spock.spock.dao.LotDao;
import ua.spock.spock.utils.EmailSender;

public class LotCloser implements Runnable {
    private Lot lot;
    private LotDao lotDao;
    private EmailSender emailSender;

    public LotCloser(Lot lot) {
        this.lot = lot;
    }

    public void setLotDao(LotDao lotDao) {
        this.lotDao = lotDao;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void run() {
        lotDao.closeLot(lot);
        lot = lotDao.getClosedLotForNotification(lot.getId());
        emailSender.sendLotClosingNotificationToOwner(lot);
        if (lot.getMaxBid() != null) {
            emailSender.sendLotClosingNotificationToBuyer(lot);
        }
    }
}
