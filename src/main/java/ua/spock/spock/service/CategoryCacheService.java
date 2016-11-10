package ua.spock.spock.service;


import ua.spock.spock.entity.Category;

import java.util.List;

public interface CategoryCacheService {
    List<Category> getAllCategories();
}
