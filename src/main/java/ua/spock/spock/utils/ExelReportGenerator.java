package ua.spock.spock.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spock.spock.entity.Lot;
import ua.spock.spock.service.BidService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExelReportGenerator {
    @Autowired
    private BidService bidService;

    @SuppressWarnings("deprecation")
    public void createReport(List<Lot> lots, String name) {
        String file="D://spock/report/"+name+".xls";
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(name);
        int i=1;
        Row row = sheet.createRow(i++);
        Cell id = row.createCell(0);
        id.setCellValue("Lot id");
        Cell title = row.createCell(1);
        title.setCellValue("Title");
        Cell description = row.createCell(2);
        description.setCellValue("Description");
        Cell category = row.createCell(3);
        category.setCellValue("Category");
        Cell startPrice = row.createCell(4);
        startPrice.setCellValue("Start price");
        Cell startDate = row.createCell(5);
        startDate.setCellValue("Start date");
        Cell endDate = row.createCell(6);
        endDate.setCellValue("End date");
        Cell bidCount = row.createCell(7);
        bidCount.setCellValue("Amount of bids");
        Cell maxBid = row.createCell(8);
        maxBid.setCellValue("Max bid ");
        for(Lot lot: lots){
            row = sheet.createRow(i++);
            id = row.createCell(0);
            id.setCellValue(lot.getId());
            title = row.createCell(1);
            title.setCellValue(lot.getTitle());
            description = row.createCell(2);
            description.setCellValue(lot.getDescription());
            category = row.createCell(3);
            category.setCellValue(lot.getCategory().getName());
            startPrice = row.createCell(4);
            startPrice.setCellValue(lot.getStartPrice());
            startDate = row.createCell(5);
            startDate.setCellValue(String.valueOf(lot.getStartDate()));
            endDate = row.createCell(6);
            endDate.setCellValue(String.valueOf(lot.getEndDate()));
            bidCount = row.createCell(7);
            bidCount.setCellValue(bidService.getBidCountForLot(lot.getId()));
            maxBid = row.createCell(8);
            if(lot.getMaxBid()!=null) {
                maxBid.setCellValue(lot.getMaxBid().getValue());
            }else{
                maxBid.setCellValue(String.valueOf(lot.getMaxBid()));
            }
        }
        try {
            book.write(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
