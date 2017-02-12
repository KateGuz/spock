package ua.spock.spock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.spock.spock.entity.ReportOption;
import ua.spock.spock.entity.User;
import ua.spock.spock.entity.UserType;
import ua.spock.spock.service.ReportService;
import ua.spock.spock.utils.ReportOptionJsonParser;


import javax.servlet.http.HttpSession;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public ResponseEntity createReport(@RequestBody String json, HttpSession session) {
        if (((User) session.getAttribute("loggedUser")).getType().equals(UserType.ADMIN)) {
            ReportOption reportOption = ReportOptionJsonParser.jsonToReportOption(json);
            reportService.createReport(reportOption);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
