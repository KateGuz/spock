package ua.spock.spock.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.CategoryDao;
import ua.spock.spock.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryCache {
    private List<Category> allCategories;
    @Autowired
    CategoryDao categoryDao;

    public List<Category> getAllCategories() {
        return new ArrayList<>(allCategories);
    }

    @Scheduled(fixedDelay = 4 * 60 * 60 * 1000, initialDelay = 0)
    private void invalidate() {
        allCategories = categoryDao.getAllCategories();
    }
}
