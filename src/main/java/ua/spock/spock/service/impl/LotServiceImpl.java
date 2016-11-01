package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.mapper.LotDao;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.LotService;

@Service
public class LotServiceImpl implements LotService{
    @Autowired
    private LotDao lotDao;

    @Override
    public Lot getLotById(int lotId) {
        return lotDao.getLotById(lotId);
    }
}
