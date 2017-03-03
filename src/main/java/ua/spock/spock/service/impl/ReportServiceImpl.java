package ua.spock.spock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.spock.spock.dao.ReportDao;
import ua.spock.spock.entity.ReportRequest;
import ua.spock.spock.service.LotService;
import ua.spock.spock.service.ReportService;
import ua.spock.spock.utils.EmailSender;
import ua.spock.spock.utils.ExcelReportGenerator;

import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private LotService lotService;
    @Autowired
    private ExcelReportGenerator excelReportGenerator;
    @Autowired
    private EmailSender emailSender;
    private ConcurrentLinkedQueue<ReportRequest> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void scheduleReport(ReportRequest reportRequest) {
        queue.offer(reportRequest);
    }

    @Override
    public int saveReport(InputStream report) {
        return reportDao.saveReport(report);
    }

    @Override
    public byte[] getReport(int reportId) {
        return reportDao.getReport(reportId);
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 0)
    private void processReports() {
        while (!queue.isEmpty()) {
            ReportRequest reportRequest = queue.poll();
            InputStream report = excelReportGenerator.createReport(lotService.getLotsForReport(reportRequest),
                    reportRequest.getDocumentName());
            int reportId = reportDao.saveReport(report);
            emailSender.sendEmail(reportRequest.getEmail(), reportId, reportRequest.getDocumentName());
        }
    }

    @Scheduled(cron = "15 15 15 ? JUL MON")
    private void cleanReports() {
        reportDao.cleanReports();
    }
}
