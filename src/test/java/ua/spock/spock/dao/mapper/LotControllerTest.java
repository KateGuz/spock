package ua.spock.spock.dao.mapper;

import org.junit.Test;
import ua.spock.spock.controller.util.LotDetailsWrapper;
import ua.spock.spock.entity.Lot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class LotControllerTest {
    @Test
    public void getTimeLeftTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plus3days = now.plusDays(3).plusHours(1);
        LocalDateTime plus1day = now.plusDays(1).plusHours(3).plusMinutes(1);
        LocalDateTime plus1day1hour = now.plusDays(1).plusHours(1).plusMinutes(1);
        LocalDateTime plus12hrs = now.plusHours(12).plusMinutes(16);
        LocalDateTime plus1hr = now.plusHours(1).plusMinutes(2);
        LocalDateTime plus25min = now.plusMinutes(26);
        Lot days = new Lot();
        days.setEndDate(plus3days);
        Lot oneDay = new Lot();
        oneDay.setEndDate(plus1day);
        Lot oneDay1hr = new Lot();
        oneDay1hr.setEndDate(plus1day1hour);
        Lot hours = new Lot();
        hours.setEndDate(plus12hrs);
        Lot oneHour = new Lot();
        oneHour.setEndDate(plus1hr);
        Lot minutes = new Lot();
        minutes.setEndDate(plus25min);
        LotDetailsWrapper controller = new LotDetailsWrapper();
        Method method = LotDetailsWrapper.class.getDeclaredMethod("getTimeLeft", Lot.class);
        method.setAccessible(true);
        String timeLeft = (String) method.invoke(controller, days);
        assertEquals("3 days", timeLeft);
        timeLeft = (String) method.invoke(controller, oneDay);
        assertEquals("1 day 3 hrs", timeLeft);
        timeLeft = (String) method.invoke(controller, oneDay1hr);
        assertEquals("1 day 1 hr", timeLeft);
        timeLeft = (String) method.invoke(controller, hours);
        assertEquals("12 hrs 15 min", timeLeft);
        timeLeft = (String) method.invoke(controller, oneHour);
        assertEquals("1 hr 1 min", timeLeft);
        timeLeft = (String) method.invoke(controller, minutes);
        assertEquals("25 min", timeLeft);
    }
}
