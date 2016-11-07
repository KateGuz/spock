package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.LotService;

import java.util.List;


@Service
public class LotServiceImpl implements LotService {
    @Autowired
    private ua.spock.spock.dao.LotDao lotDao;

    @Override
    public List<Lot> getAll() {
        return lotDao.getAll();
    }

    @Override
    public List<Lot> getByCategory(int categoryId) {
        return lotDao.getByCategory(categoryId);
    }

    public Lot getById(int lotId) {
        return lotDao.getById(lotId);

    }
}
