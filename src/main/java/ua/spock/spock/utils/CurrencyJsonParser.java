package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CurrencyJsonParser {
    public static String jsonToUrl(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            JSONObject jsonObject = (JSONObject) object;
            return String.valueOf(jsonObject.get("url"));
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to url", e);
        }

    }

    public static String jsonToCurrency(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            JSONObject jsonObject = (JSONObject) object;
            return String.valueOf(jsonObject.get("currency"));
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to currency", e);
        }

    }
}
