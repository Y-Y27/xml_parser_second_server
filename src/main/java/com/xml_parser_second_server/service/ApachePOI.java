package com.xml_parser_second_server.service;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class ApachePOI {
    public void createExcelDocument() throws IOException {
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet("Test");

        sheet.autoSizeColumn(1);
        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);

        Cell name = row.createCell(0);
        name.setCellValue("First Column");



        Cell lastName = row.createCell(2);
        lastName.setCellValue("Second Column");

        String filePath = "D:\\IDEA Projects\\xml_parser_second_server\\src\\main\\resources\\excel.xls";

        workbook.write(new FileOutputStream(filePath));
        workbook.close();

    }

    public static void main(String[] args) throws IOException {
        ApachePOI apachePOI = new ApachePOI();
        apachePOI.createExcelDocument();
    }


}
