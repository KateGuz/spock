package ua.spock.spock.dao;

import ua.spock.spock.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories();
}
