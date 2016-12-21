package ua.spock.spock.controller.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;


@Service
public class App {
    private HashMap<Integer, Double> map = new HashMap<>();

    public HashMap<Integer, Double> exec() {
        BufferedReader reader = null;
        try {
            URL site = new URL("http://minfin.com.ua/currency/");
            reader = new BufferedReader(new InputStreamReader(site.openStream()));
            String line;
            int i = 1;
            while ((line = reader.readLine()) != null) {
                if (line.contains("mfcur-nbu-full-wrap")) {
                    String temp = reader.readLine();
                    map.put(i++, Double.valueOf(temp.substring(0, temp.indexOf("<"))));
                }
            }

            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}