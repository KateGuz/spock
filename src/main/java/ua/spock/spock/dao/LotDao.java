package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;
import ua.spock.spock.filter.LotFilter;

import java.util.List;

public interface LotDao {
    List<Lot> get(LotFilter lotFilter);
    Lot getById(int lotId);
    List<Lot> getByUser(int userId);
    void add(Lot lot);
    void delete(int id);
    void edit(Lot lot);
}
