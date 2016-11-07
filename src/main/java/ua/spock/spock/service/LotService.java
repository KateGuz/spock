package ua.spock.spock.service;

import ua.spock.spock.entity.Lot;


import java.util.List;

public interface LotService {
    List<Lot> getAll();
    List<Lot> getByCategory(int categoryId);
    Lot getById(int lotId);
}
