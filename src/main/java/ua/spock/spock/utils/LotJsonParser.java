package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.spock.spock.entity.*;

import java.time.LocalDateTime;

public class LotJsonParser {
    public static Lot jsonToLot(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            Lot lot = new Lot();
            JSONObject jsonObject = (JSONObject) object;
            String title = String.valueOf(jsonObject.get("title"));
            lot.setTitle(title);
            String description = String.valueOf(jsonObject.get("description"));
            lot.setDescription(description);
            double startPrice= Double.parseDouble(String.valueOf(jsonObject.get("startPrice")));
            lot.setStartPrice(startPrice);
            double minStep = Double.parseDouble(String.valueOf(jsonObject.get("minStep")));
            lot.setMinStep(minStep);
           LocalDateTime startDate = LocalDateTime.parse(String.valueOf(jsonObject.get("startDate")));
            lot.setStartDate(startDate);
            LocalDateTime endDate = LocalDateTime.parse(String.valueOf(jsonObject.get("endDate")));
            lot.setEndDate(endDate);
            if (jsonObject.containsKey("quickBuyPrice")) {
                double quickBuyPrice = Double.parseDouble(String.valueOf(jsonObject.get("quickBuyPrice")));
                lot.setQuickBuyPrice(quickBuyPrice);
            }
            int userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
            User user=new User();
            user.setId(userId);
            lot.setUser(user);
            int categoryId = Integer.parseInt(String.valueOf(jsonObject.get("categoryId")));
            Category category=new Category();
            category.setId(categoryId);
            lot.setCategory(category);
            lot.setType(LotType.IN_PROGRESS);
            lot.setCurrency(Currency.UAH);
            return lot;
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to user", e);
        }

    }
}
