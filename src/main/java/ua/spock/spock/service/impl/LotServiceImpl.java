package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.entity.Lot;
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

    public Lot getById(int lotId) {
        return lotDao.getById(lotId);
    }

    @Override
    public List<Lot> getUserLots(int userId) {
        return lotDao.get(userId);
    }
}
