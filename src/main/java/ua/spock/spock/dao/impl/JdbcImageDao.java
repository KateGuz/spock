package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.ImageDao;
import ua.spock.spock.entity.Image;

@Repository
public class JdbcImageDao implements ImageDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getImageSQL;
    @Autowired
    private String getUserImageSQL;
    @Autowired
    private String editPrimaryLotImageSQL;
    @Autowired
    private String savePrimaryLotImageSQL;
    @Autowired
    private String saveSecondaryLotImageSQL;
    @Autowired
    private String saveUserImageSQL;

    @Override
    public Image getImage(int imageId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("imageId", imageId);
        Image image = new Image();
        image.setBytes(namedParameterJdbcTemplate.queryForObject(getImageSQL, params, byte[].class));
        return image;
    }

    @Override
    public Image getUserImage(int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        Image image = new Image();
        image.setBytes(namedParameterJdbcTemplate.queryForObject(getUserImageSQL, params, byte[].class));
        return image;
    }

    @Override
    public void saveUserImage(Image image) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", image.getId());
        params.addValue("image", image.getBytes());
        namedParameterJdbcTemplate.update(saveUserImageSQL, params);
    }

    @Override
    public void saveLotImage(Image image) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("lotId", image.getId());
        params.addValue("type", image.getType());
        params.addValue("image", image.getBytes());
        if (image.getType().equals("M")) {
            namedParameterJdbcTemplate.update(savePrimaryLotImageSQL, params);
      } else {
         namedParameterJdbcTemplate.update(saveSecondaryLotImageSQL, params);
      }
    }

    @Override
    public void editPrimaryLotImage(Image image) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("lotId", image.getId());
        params.addValue("type", image.getType());
        params.addValue("image", image.getBytes());
        namedParameterJdbcTemplate.update(editPrimaryLotImageSQL, params);
    }
}