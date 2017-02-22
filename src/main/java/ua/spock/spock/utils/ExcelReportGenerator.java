package ua.spock.spock.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.BidService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelReportGenerator {
    @Autowired
    private BidService bidService;
    @Autowired
    private ServletContext servletContext;
    private String reportFolderPath;

    @SuppressWarnings("deprecation")
    public void createReport(List<Lot> lots, String name) {
        String file = reportFolderPath + name + ".xlsx";
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet(name);
        int rowNum = 1;
        setSheetHeader(sheet);

        for (Lot lot : lots) {
            Row row = sheet.createRow(rowNum++);
            Cell id = row.createCell(0);
            id.setCellValue(lot.getId());
            Cell title = row.createCell(1);
            title.setCellValue(lot.getTitle());
            Cell description = row.createCell(2);
            description.setCellValue(lot.getDescription());
            Cell category = row.createCell(3);
            category.setCellValue(lot.getCategory().getName());
            Cell startPrice = row.createCell(4);
            startPrice.setCellValue(lot.getStartPrice());
            Cell startDate = row.createCell(5);
            startDate.setCellValue(String.valueOf(lot.getStartDate()));
            Cell endDate = row.createCell(6);
            endDate.setCellValue(String.valueOf(lot.getEndDate()));
            Cell bidCount = row.createCell(7);
            bidCount.setCellValue(bidService.getBidCountForLot(lot.getId()));
            Cell maxBid = row.createCell(8);
            if (lot.getMaxBid() != null) {
                maxBid.setCellValue(lot.getMaxBid().getValue());
            } else {
                maxBid.setCellValue(String.valueOf(lot.getMaxBid()));
            }
        }
        try (FileOutputStream stream = new FileOutputStream(file)) {
            book.write(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSheetHeader(Sheet sheet) {
        Row row = sheet.createRow(0);
        String[] headers = {"Lot id", "Title", "Description", "Category", "Start price",
                "Start date", "End date","Amount of bids", "Max bid"};
        for (int col = 0; col < 9; col++) {
            Cell cell = row.createCell(col);
            cell.setCellValue(headers[col]);
        }
    }

    @PostConstruct
    private void setReportFolderPath() {
        reportFolderPath = servletContext.getRealPath("/report/");
    }
}
