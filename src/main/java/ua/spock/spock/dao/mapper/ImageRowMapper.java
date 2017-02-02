package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageRowMapper implements RowMapper<byte[]>{
    @Override
    public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getBytes(1);
    }
}
