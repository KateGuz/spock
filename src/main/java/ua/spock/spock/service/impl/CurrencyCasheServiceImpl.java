package ua.spock.spock.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.service.CurrencyCasheService;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class CurrencyCasheServiceImpl implements CurrencyCasheService {
    private HashMap<String, Double> allCurrencyValue;
    private HashMap<String, Double> currencyMap = new HashMap<>();

    @Override
    public HashMap<String, Double> getCurrencyValue() {
        return new HashMap<>(allCurrencyValue);
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000, initialDelay = 0)
    private void invalidate() {
        allCurrencyValue = currencyJsonParser();
    }

    private HashMap<String, Double> currencyJsonParser() {
        JSONParser parser = new JSONParser();
        HttpURLConnection urlConnection;
        BufferedReader reader;
        String resultJson = "";
        try {
            URL url = new URL("http://finance.ua/currency/data?for=currency-cash");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultJson = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object object = null;
        try {
            object = parser.parse(resultJson);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray currencyForPeriodList = (JSONArray) object;
        JSONArray currencyForThisDay = (JSONArray) currencyForPeriodList.get(currencyForPeriodList.size() - 1);
        currencyMap.put("UAH", 1.0);
        currencyMap.put("USD", Double.valueOf(String.valueOf(currencyForThisDay.get(1))));
        currencyMap.put("EUR", Double.valueOf(String.valueOf(currencyForThisDay.get(2))));
        return currencyMap;
    }
}
