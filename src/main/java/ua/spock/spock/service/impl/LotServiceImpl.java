package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.filter.LotFilter;
import ua.spock.spock.service.LotService;

import java.util.List;

@Service
public class LotServiceImpl implements LotService {
    @Autowired
    private LotDao lotDao;

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
}
