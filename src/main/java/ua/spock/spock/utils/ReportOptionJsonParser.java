package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.spock.spock.entity.ReportOption;

import java.time.LocalDateTime;

public class ReportOptionJsonParser {
    public static ReportOption jsonToReportOption(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            ReportOption reportOption = new ReportOption();
            JSONObject jsonObject = (JSONObject) object;
            LocalDateTime startDate = LocalDateTime.parse(String.valueOf(jsonObject.get("startDate")));
            reportOption.setStartDate(startDate);
            LocalDateTime endDate = LocalDateTime.parse(String.valueOf(jsonObject.get("endDate")));
            reportOption.setEndDate(endDate);
            reportOption.setDocumentName("report-" + LocalDateTime.now().getYear() + "-" + LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getDayOfMonth());
            String email = String.valueOf(jsonObject.get("userEmail"));
            reportOption.setEmail(email);
            String type = String.valueOf(jsonObject.get("type"));
            reportOption.setType(type);
            return reportOption;
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to user", e);
        }

    }
}
