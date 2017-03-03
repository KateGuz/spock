package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;
import ua.spock.spock.service.ReportService;
import ua.spock.spock.utils.ReportRequestJsonParser;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public ResponseEntity createReport(@RequestBody String json, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user.getType().equals(UserType.ADMIN)) {
            ReportRequest reportRequest = ReportRequestJsonParser.jsonToReportOption(json);
            reportRequest.setEmail(user.getEmail());
            reportService.scheduleReport(reportRequest);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/report/{reportId}/**", method = RequestMethod.GET)
    public void getReport(HttpServletResponse response, @PathVariable int reportId) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        byte[] report = reportService.getReport(reportId);
        response.getOutputStream().write(report);
    }
}
