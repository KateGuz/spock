package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.BidDao;
import ua.spock.spock.entity.Bid;
import ua.spock.spock.service.BidService;
import ua.spock.spock.service.LotService;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private BidDao bidDao;
    @Autowired
    private LotService lotService;

    @Override
    public int getBidCountForLot(int lotId) {
        return bidDao.getBidCountForLot(lotId);
    }

    @Override
    public void placeBid(Bid bid) {
        int bidId = bidDao.placeBid(bid);
        lotService.updateMaxBidId(bid.getLot(), bidId);
    }
}
