package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.SortType;

import java.util.List;

public interface LotDao {
    List<Lot> getAll(SortType sortType);
    Lot getById(int lotId);
}
