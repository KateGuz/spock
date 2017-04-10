package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.spock.spock.entity.User;

public class UserJsonParser {
    public static User jsonToUser(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            User user = new User();
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.containsKey("name")) {
                String name = String.valueOf(jsonObject.get("name"));
                user.setName(name);
            }
            String password = String.valueOf(jsonObject.get("password"));
            user.setPassword(password);
            if (jsonObject.containsKey("email")) {
                String email = String.valueOf(jsonObject.get("email"));
                user.setEmail(email);
            }
            return user;
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to user", e);
        }

    }
}
