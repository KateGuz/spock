package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.ReportOption;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.ReportService;
import ua.spock.spock.utils.EmailSender;
import ua.spock.spock.utils.ExelReportGenerator;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private LotService lotService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private ExelReportGenerator exelReportGenerator;

    @Override
    public void createReport(ReportOption reportOption) {
        exelReportGenerator.createReport(lotService.getLotsForReport(reportOption),reportOption.getDocumentName());
        emailSender.sendEmail(reportOption.getEmail(),reportOption.getDocumentName());
    }
}
