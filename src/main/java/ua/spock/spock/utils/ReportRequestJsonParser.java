package ua.spock.spock.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.entity.ReportRequestType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReportRequestJsonParser {
    public ReportRequest jsonToReportRequest(String json) {
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
            System.out.println(json);
            System.out.println(reportRequest);
            return reportRequest;
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while converting json to user", e);
        }

    }
}
