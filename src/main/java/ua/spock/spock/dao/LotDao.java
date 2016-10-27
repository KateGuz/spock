package ua.spock.spock.dao;

import ua.spock.spock.entity.Lot;

import java.util.List;

public interface LotDao {
    List<Lot> getAll();

    List<Lot> getLotsByCategory(int categoryId);

}
