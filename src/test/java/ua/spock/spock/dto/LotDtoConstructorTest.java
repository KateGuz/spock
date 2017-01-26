package ua.spock.spock.dto;

import org.junit.Test;
import ua.spock.spock.entity.Lot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class LotDtoConstructorTest {

    @Test
    public void testGetTimeLeft() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
        Lot oneDayOneHr = new Lot();
        oneDayOneHr.setEndDate(plus1day1hour);
        Lot hours = new Lot();
        hours.setEndDate(plus12hrs);
        Lot oneHour = new Lot();
        oneHour.setEndDate(plus1hr);
        Lot minutes = new Lot();
        minutes.setEndDate(plus25min);
        LotDtoConstructor constructor = new LotDtoConstructor();
        Method method = LotDtoConstructor.class.getDeclaredMethod("getTimeLeft", Lot.class);
        method.setAccessible(true);
        String timeLeft = (String) method.invoke(constructor, days);
        assertEquals("3 дн", timeLeft);
        timeLeft = (String) method.invoke(constructor, oneDay);
        assertEquals("1 дн 3 час", timeLeft);
        timeLeft = (String) method.invoke(constructor, oneDayOneHr);
        assertEquals("1 дн 1 час", timeLeft);
        timeLeft = (String) method.invoke(constructor, hours);
        assertEquals("12 час 15 мин", timeLeft);
        timeLeft = (String) method.invoke(constructor, oneHour);
        assertEquals("1 час 1 мин", timeLeft);
        timeLeft = (String) method.invoke(constructor, minutes);
        assertEquals("25 мин", timeLeft);
        method.setAccessible(false);
    }

}