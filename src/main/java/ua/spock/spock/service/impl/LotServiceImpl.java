package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.LotDao;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.SortType;
import ua.spock.spock.service.LotService;

import java.util.List;

@Service
public class LotServiceImpl implements LotService {
    @Autowired
    private LotDao lotDao;

    @Override
    public List<Lot> getAll(SortType sortType) {
        return lotDao.getAll(sortType);
    }

    public Lot getById(int lotId) {
        return lotDao.getById(lotId);
    }
}
