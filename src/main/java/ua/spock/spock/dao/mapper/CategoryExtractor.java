package ua.spock.spock.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryExtractor implements ResultSetExtractor<List<Category>> {

    @Override
    public List<Category> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Category> allCategories = new ArrayList<>();
        Category parent = null;
        while (rs.next()) {
                if (parent == null || parent.getId() != rs.getInt("parentId")) {
                    parent = new Category();
                    parent.setId(rs.getInt("parentId"));
                    parent.setName(rs.getString("parentName"));
                    allCategories.add(parent);
                }
                Category child = new Category();
                child.setId(rs.getInt("id"));
                child.setName(rs.getString("name"));
                child.setParent(parent);
                parent.getChildren().add(child);
            }

        return allCategories;
    }
}
