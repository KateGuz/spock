package ua.spock.spock.dao;

import ua.spock.spock.entity.Bid;

public interface BidDao {
    int getBidCountForLot(int lotId);
    int placeBid(Bid bid);
}
