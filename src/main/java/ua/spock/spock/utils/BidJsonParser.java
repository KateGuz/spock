package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.spock.spock.entity.Bid;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.entity.User;

public class BidJsonParser {
    public static Bid jsonToBid(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
           Bid bid=new Bid();
            JSONObject jsonObject = (JSONObject) object;
            double bidValue = Double.parseDouble(String.valueOf(jsonObject.get("bid")));
            bid.setValue(bidValue);
            int userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
            User user=new User();
            user.setId(userId);
            bid.setUser(user);
            int lotId = Integer.parseInt(String.valueOf(jsonObject.get("lotId")));
            Lot lot= new Lot();
            lot.setId(lotId);
            bid.setLot(lot);
            return bid;
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to user", e);
        }

    }
}
