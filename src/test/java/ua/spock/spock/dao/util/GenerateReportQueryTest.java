package ua.spock.spock.dao.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.entity.ReportRequestType;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/root-context.xml"})
public class GenerateReportQueryTest {
    @Autowired
    QueryGenerator queryGenerator;

    @Test
    public void startedLotsTest() {
        ReportRequest request = new ReportRequest();
        request.setEmail("d.zrazhevskiy@gmail.com");
        request.setStartDate(LocalDateTime.parse("2016-11-01T00:00"));
        request.setEndDate(LocalDateTime.parse("2017-03-04T00:00"));
        request.setType(ReportRequestType.getReportRequestTypeById("started"));
        String reportQuery = queryGenerator.generateReportQuery(request);
        reportQuery = reportQuery.replaceAll("\\s+"," ");
        String referenceQuery = "SELECT l.id, l.title, l.description, l.categoryId, c.parentId AS parentCategoryId," +
                " c.name AS categoryName, cp.name AS parentCategoryName, l.userId, u.name AS userName, l.startDate," +
                " l.endDate, l.startPrice, l.minStep, l.quickBuyPrice, l.type, l.maxBidId, b.bid AS maxBidValue " +
                "FROM lot AS l JOIN user AS u ON u.id=l.userId JOIN category AS c ON c.id=l.categoryId " +
                "JOIN category AS cp ON cp.id=c.parentId left JOIN bid as b ON b.id=l.maxBidId " +
                "WHERE (l.startDate>'2016-11-01T00:00' AND l.startDate<'2017-03-04T00:00');";
        assertEquals(referenceQuery, reportQuery);
    }

    @Test
    public void endedLotsTest() {
        ReportRequest request = new ReportRequest();
        request.setEmail("d.zrazhevskiy@gmail.com");
        request.setStartDate(LocalDateTime.parse("2016-12-23T00:00"));
        request.setEndDate(LocalDateTime.parse("2017-02-24T00:00"));
        request.setType(ReportRequestType.getReportRequestTypeById("ended"));
        String reportQuery = queryGenerator.generateReportQuery(request);
        reportQuery = reportQuery.replaceAll("\\s+"," ");
        String referenceQuery = "SELECT l.id, l.title, l.description, l.categoryId, c.parentId AS parentCategoryId," +
                " c.name AS categoryName, cp.name AS parentCategoryName, l.userId, u.name AS userName, l.startDate," +
                " l.endDate, l.startPrice, l.minStep, l.quickBuyPrice, l.type, l.maxBidId, b.bid AS maxBidValue " +
                "FROM lot AS l JOIN user AS u ON u.id=l.userId JOIN category AS c ON c.id=l.categoryId " +
                "JOIN category AS cp ON cp.id=c.parentId left JOIN bid as b ON b.id=l.maxBidId " +
                "WHERE (l.endDate>'2016-12-23T00:00' AND l.endDate<'2017-02-24T00:00');";
        assertEquals(referenceQuery, reportQuery);
    }
}
