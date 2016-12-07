package ua.spock.spock.service;

import ua.spock.spock.entity.Bid;

public interface BidService {
    int getBidCountForLot(int lotId);
    void placeBid(Bid bid);
}
