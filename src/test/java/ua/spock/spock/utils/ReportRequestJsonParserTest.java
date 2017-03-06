package ua.spock.spock.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.entity.ReportRequestType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/root-context.xml"})
public class ReportRequestJsonParserTest {
    @Autowired
    private ReportRequestJsonParser parser;
    @Test
    public void jsonToReportRequestTest() {
        ReportRequest expected = new ReportRequest();
        expected.setStartDate(LocalDateTime.parse("2016-12-16T00:00"));
        expected.setEndDate(LocalDateTime.parse("2017-02-09T00:00"));
        expected.setType(ReportRequestType.getReportRequestTypeById("started"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expected.setDocumentName("report-" + LocalDateTime.now().format(dateTimeFormatter));
        String json = "{\"startDate\":\"2016-12-16T00:00\", \"type\" :\"started\", \"endDate\" :\"2017-02-09T00:00\"}";
        ReportRequest actual = parser.jsonToReportRequest(json);
        assertEquals(expected.getDocumentName(), actual.getDocumentName());
        assertEquals(expected.getEndDate(), actual.getEndDate());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getType(), actual.getType());
    }
}
