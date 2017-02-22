package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.spock.spock.entity.ReportOption;
import ua.spock.spock.entity.ReportOptionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportOptionJsonParser {
    public static ReportOption jsonToReportOption(String json, String userName) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            ReportOption reportOption = new ReportOption();
            JSONObject jsonObject = (JSONObject) object;
            LocalDateTime startDate = LocalDateTime.parse(String.valueOf(jsonObject.get("startDate")));
            reportOption.setStartDate(startDate);
            LocalDateTime endDate = LocalDateTime.parse(String.valueOf(jsonObject.get("endDate")));
            reportOption.setEndDate(endDate);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh.mm.ss.SSS");
            reportOption.setDocumentName("report-" + userName + "-" + LocalDateTime.now().format(dateTimeFormatter));
            String type = String.valueOf(jsonObject.get("type"));
            reportOption.setType(ReportOptionType.getRportOptionTypeById(type));
            return reportOption;
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to user", e);
        }

    }
}
