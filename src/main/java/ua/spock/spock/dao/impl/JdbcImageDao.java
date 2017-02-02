package ua.spock.spock.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.spock.spock.dao.ImageDao;
import ua.spock.spock.dao.mapper.ImageRowMapper;
import ua.spock.spock.dao.mapper.LotMainImageIdsExtractor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcImageDao implements ImageDao{
    private static final ImageRowMapper IMAGE_ROW_MAPPER = new ImageRowMapper();
    private static final LotMainImageIdsExtractor LOT_MAIN_IMAGE_IDS_EXTRACTOR = new LotMainImageIdsExtractor();
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getImageSQL;
    @Autowired
    private String getLotImageIdsSQL;
    @Autowired
    private String getLotMainImageIdsSQL;
    @Autowired
    private String saveImageSQL;

    @Override
    public byte[] getImage(int imageId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("imageId", imageId);
        return namedParameterJdbcTemplate.queryForObject(getImageSQL, params, byte[].class);
    }

    @Override
    public List<Integer> getIds(int lotId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("lotId", lotId);
        return namedParameterJdbcTemplate.queryForList(getLotImageIdsSQL, params, Integer.class);
    }

    @Override
    public Map<Integer, Integer> getIds(List<Integer> lotIds) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("lotIds", lotIds);
        return namedParameterJdbcTemplate.query(getLotMainImageIdsSQL, params, LOT_MAIN_IMAGE_IDS_EXTRACTOR);
    }

    @Override
    public void saveImage(int lotId, InputStream imageStream) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("lotId", lotId);
        params.addValue("image", imageStream);
        namedParameterJdbcTemplate.update(saveImageSQL, params);
    }
}
