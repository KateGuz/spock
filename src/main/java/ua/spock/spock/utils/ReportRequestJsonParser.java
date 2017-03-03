package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.entity.ReportRequestType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportRequestJsonParser {
    public static ReportRequest jsonToReportOption(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            ReportRequest reportRequest = new ReportRequest();
            JSONObject jsonObject = (JSONObject) object;
            LocalDateTime startDate = LocalDateTime.parse(String.valueOf(jsonObject.get("startDate")));
            reportRequest.setStartDate(startDate);
            LocalDateTime endDate = LocalDateTime.parse(String.valueOf(jsonObject.get("endDate")));
            reportRequest.setEndDate(endDate);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            reportRequest.setDocumentName("report-" + LocalDateTime.now().format(dateTimeFormatter));
            String type = String.valueOf(jsonObject.get("type"));
            reportRequest.setType(ReportRequestType.getReportRequestTypeById(type));
            return reportRequest;
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to user", e);
        }

    }
}
