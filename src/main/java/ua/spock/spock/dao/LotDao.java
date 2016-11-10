package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.filter.LotFilter;

import java.util.List;

public interface LotDao {
    List<Lot> get(LotFilter lotFilter);
    Lot getById(int lotId);
}
