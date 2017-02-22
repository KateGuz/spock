package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.ReportOption;
import ua.spock.spock.service.ReportService;
import ua.spock.spock.service.scheduler.ReportProcessingScheduler;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportProcessingScheduler reportProcessingScheduler;

    @Override
    public void scheduleReport(ReportOption reportOption) {
        reportProcessingScheduler.scheduleReport(reportOption);
    }
}
