package ua.spock.spock.utils;

import ua.spock.spock.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;

public class LotRequestParser {
    public static Lot requestToLot(HttpServletRequest httpServletRequest) {
        Lot lot = new Lot();
        String title = httpServletRequest.getParameter("title");
        lot.setTitle(title);
        String description = httpServletRequest.getParameter("description");
        lot.setDescription(description);
        double startPrice = Double.parseDouble(String.valueOf(httpServletRequest.getParameter("startPrice")));
        lot.setStartPrice(startPrice);
        double minStep = Double.parseDouble(String.valueOf(httpServletRequest.getParameter("minStep")));
        lot.setMinStep(minStep);
        LocalDateTime startDate = LocalDateTime.parse(String.valueOf(httpServletRequest.getParameter("startDate")));
        lot.setStartDate(startDate);
        LocalDateTime endDate = LocalDateTime.parse(String.valueOf(httpServletRequest.getParameter("endDate")));
        lot.setEndDate(endDate);
        if (httpServletRequest.getParameter("quickBuyPrice") != null) {
            double quickBuyPrice = Double.parseDouble(String.valueOf(httpServletRequest.getParameter("quickBuyPrice")));
            lot.setQuickBuyPrice(quickBuyPrice);
        }
        int userId = Integer.parseInt(String.valueOf(httpServletRequest.getParameter("userId")));
        User user = new User();
        user.setId(userId);
        lot.setUser(user);
        int categoryId = Integer.parseInt(String.valueOf(httpServletRequest.getParameter("categoryId")));
        Category category = new Category();
        category.setId(categoryId);
        lot.setCategory(category);
        LocalDateTime now = LocalDateTime.now();
        Duration interval = Duration.between(now, lot.getStartDate());
        if (interval.isNegative()) {
            lot.setType(LotType.IN_PROGRESS);
        } else {
            lot.setType(LotType.PENDING);
        }
       return lot;
    }
}
