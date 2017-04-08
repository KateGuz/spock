package ua.spock.spock.dao.mapper;

import org.junit.Test;
import ua.spock.spock.entity.User;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserForNotificationRowMapperTest {
    @Test
    public void mapRow() throws Exception {
        UserForNotificationRowMapper mapper = new UserForNotificationRowMapper();
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("email")).thenReturn("test@spock.com");
        User user = mapper.mapRow(rs, 1);
        assertEquals("test@spock.com", user.getEmail());
    }

}