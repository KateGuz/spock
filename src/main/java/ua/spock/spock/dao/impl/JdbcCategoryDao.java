package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.CategoryDao;
import ua.spock.spock.dao.mapper.CategoryExtractor;
import ua.spock.spock.entity.Category;

import java.util.List;

@Repository
public class JdbcCategoryDao implements CategoryDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    CategoryExtractor categoryExtractor;
    @Autowired
    String getAllCategoriesSQL;

    @Override
    public List<Category> getAllCategories() {
        return namedParameterJdbcTemplate.query(getAllCategoriesSQL, categoryExtractor);
    }
}
