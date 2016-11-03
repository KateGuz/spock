package ua.spock.spock.dao.mapper;

import ua.spock.spock.entity.Lot;

public interface LotDao {
    Lot getLotById(int lotId);
}
