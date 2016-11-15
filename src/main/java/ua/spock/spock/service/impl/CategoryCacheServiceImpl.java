package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.CategoryDao;
import ua.spock.spock.entity.Category;
import ua.spock.spock.service.CategoryCacheService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryCacheServiceImpl implements CategoryCacheService {
    private List<Category> allCategories;
    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(allCategories);
    }

    @Scheduled(fixedDelay = 4 * 60 * 60 * 1000, initialDelay = 0)
    private void invalidate() {
        allCategories = categoryDao.getAllCategories();
    }
}
