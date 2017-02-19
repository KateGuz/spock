package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.ImageDao;
import ua.spock.spock.entity.Image;

import java.io.InputStream;

@Repository
public class JdbcImageDao implements ImageDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getImageSQL;
    @Autowired
    private String saveImageSQL;

    @Override
    public Image getImage(int imageId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("imageId", imageId);
        Image image=new Image();
         image.setImage(namedParameterJdbcTemplate.queryForObject(getImageSQL, params, byte[].class));
        return image;
    }

    @Override
    public void saveImage(int lotId, InputStream imageStream) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("lotId", lotId);
        params.addValue("image", imageStream);
        namedParameterJdbcTemplate.update(saveImageSQL, params);
    }
}
