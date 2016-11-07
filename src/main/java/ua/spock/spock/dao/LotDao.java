package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;

import java.util.List;

public interface LotDao {
    List<Lot> getAll();
    Lot getById(int lotId);
    List<Lot> getByCategory(int categoryId);
}
