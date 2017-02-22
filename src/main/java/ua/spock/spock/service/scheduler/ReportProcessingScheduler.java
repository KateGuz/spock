package ua.spock.spock.service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.ReportOption;
import ua.spock.spock.service.LotService;
import ua.spock.spock.utils.EmailSender;
import ua.spock.spock.utils.ExcelReportGenerator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ReportProcessingScheduler {
    @Autowired
    private LotService lotService;
    @Autowired
    private ExcelReportGenerator excelReportGenerator;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private ServletContext servletContext;
    private String reportFolderPath;
    private ConcurrentLinkedQueue<ReportOption> queue = new ConcurrentLinkedQueue<>();

    public void scheduleReport(ReportOption reportOption) {
        queue.offer(reportOption);
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 0)
    private void processReports() {
        while (!queue.isEmpty()) {
            ReportOption reportOption = queue.poll();
            excelReportGenerator.createReport(lotService.getLotsForReport(reportOption), reportOption.getDocumentName());
            emailSender.sendEmail(reportOption.getEmail(), reportOption.getDocumentName());
        }
    }

    @Scheduled(fixedDelay = 2 * 24 * 60 * 60 * 1000)
    private void cleanReportsFolder() {
        File reportsFolder = new File(reportFolderPath);
        long now = System.currentTimeMillis();
        for (File report : reportsFolder.listFiles()) {
            long diff = now - report.lastModified();
            if (diff > 2 * 24 * 60 * 60 * 1000) {
                report.delete();
            }
        }
    }

    @PostConstruct
    private void setReportFolderPath() {
        reportFolderPath = servletContext.getRealPath("/report/");
        File reportFolder = new File(reportFolderPath);
        if (!reportFolder.isDirectory()) {
            reportFolder.mkdir();
        }
    }
}
