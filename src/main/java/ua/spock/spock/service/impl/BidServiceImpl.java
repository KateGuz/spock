package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.mapper.BidDao;
import ua.spock.spock.service.BidService;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private BidDao bidDao;

    @Override
    public int getBidCountForLot(int lotId) {
        return bidDao.getBidCountForLot(lotId);
    }
}
