package ua.spock.spock.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.spock.spock.entity.Lot;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LotForClosingRowMapper implements RowMapper<Lot> {
    @Override
    public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lot lot = new Lot();
        lot.setId(rs.getInt("id"));
        lot.setEndDate(rs.getTimestamp("endDate").toLocalDateTime());
        return lot;
    }
}
