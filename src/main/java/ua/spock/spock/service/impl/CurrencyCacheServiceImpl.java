package ua.spock.spock.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.service.CurrencyCacheService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class CurrencyCacheServiceImpl implements CurrencyCacheService {

    private HashMap<String, Double> rates = new HashMap<>();

    @Override
    public HashMap<String, Double> getRates() {
        return new HashMap<>(rates);
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000, initialDelay = 0)
    private void getRatesFromAPI() {
        JSONParser parser = new JSONParser();
        HttpURLConnection urlConnection;
        BufferedReader reader;
        String resultJson;
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
            throw new RuntimeException("Error occurred while converting json", e);
        }
        Object object;
        try {
            object = parser.parse(resultJson);
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json", e);
        }
        JSONArray currencyForPeriodList = (JSONArray) object;
        JSONArray currencyForThisDay = (JSONArray) currencyForPeriodList.get(currencyForPeriodList.size() - 1);
        rates.put("UAH", 1.0);
        rates.put("USD", Double.valueOf(String.valueOf(currencyForThisDay.get(1))));
        rates.put("EUR", Double.valueOf(String.valueOf(currencyForThisDay.get(2))));
    }
}
