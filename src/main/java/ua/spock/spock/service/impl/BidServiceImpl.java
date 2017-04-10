package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.BidDao;
import ua.spock.spock.entity.Bid;
import ua.spock.spock.entity.User;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.UserService;
import ua.spock.spock.utils.EmailSender;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private BidDao bidDao;
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSender emailSender;

    @Override
    public int getBidCountForLot(int lotId) {
        return bidDao.getBidCountForLot(lotId);
    }

    @Override
    public void add(Bid bid) {
        int bidId = bidDao.add(bid);
        bid.setId(bidId);
        lotService.updateMaxBidId(bid.getLot(), bidId);
        bid.setLot(lotService.getById(bid.getLot().getId()));
        List<User> recipients = userService.getUsersForNotification(bid.getLot());
        emailSender.sendNewBidNotifications(bid, recipients);
    }
}
