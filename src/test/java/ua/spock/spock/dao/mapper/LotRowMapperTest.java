package ua.spock.spock.dao.mapper;

import org.junit.Test;
import static org.mockito.Mockito.*;
import ua.spock.spock.dao.mapper.util.QueryType;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.LotType;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class LotRowMapperTest {
    @Test
    public void mapRow() throws Exception {
        //prepare
        LotRowMapper lotRowMapper = new LotRowMapper(QueryType.GET_ONE_LOT);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("Title");
        when(resultSet.getTimestamp("startDate")).thenReturn(Timestamp.valueOf("2016-01-01 12:10:04"));
        when(resultSet.getTimestamp("endDate")).thenReturn(Timestamp.valueOf("2016-01-01 12:10:04"));
        when(resultSet.getDouble("startPrice")).thenReturn(10.40);
        when(resultSet.getInt("maxBidId")).thenReturn(1);
        when(resultSet.getString("currency")).thenReturn("USD");
        when(resultSet.getDouble("quickBuyPrice")).thenReturn(100.00);
        when(resultSet.getString("type")).thenReturn("C");
        when(resultSet.getString("description")).thenReturn("Description");
        when(resultSet.getInt("categoryId")).thenReturn(1);
        when(resultSet.getString("categoryName")).thenReturn("Category");
        when(resultSet.getInt("parentCategoryId")).thenReturn(1);
        when(resultSet.getString("parentCategoryName")).thenReturn("Parent category");
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getString("userName")).thenReturn("User");
        when(resultSet.getDouble("minStep")).thenReturn(10.00);
        //when
        Lot lot = lotRowMapper.mapRow(resultSet, 1);
        //then
        assertEquals(1, lot.getId());
        assertEquals("Title", lot.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertEquals(LocalDateTime.parse("2016-01-01 12:10:04", formatter), lot.getStartDate());
        assertEquals(LocalDateTime.parse("2016-01-01 12:10:04", formatter), lot.getEndDate());
        assertEquals(10.40, lot.getStartPrice(), 0.001);
        assertEquals(1, lot.getMaxBid().getId());
        assertEquals(100.00, lot.getQuickBuyPrice(), 0.001);
        assertEquals(LotType.CLOSED, lot.getType());
        assertEquals("Description", lot.getDescription());
        assertEquals(1, lot.getCategory().getId());
        assertEquals("Category", lot.getCategory().getName());
        assertEquals(1, lot.getCategory().getParent().getId());
        assertEquals("Parent category", lot.getCategory().getParent().getName());
        assertEquals(1, lot.getUser().getId());
        assertEquals("User", lot.getUser().getName());
        assertEquals(10.00, lot.getMinStep(), 0.001);
    }
}