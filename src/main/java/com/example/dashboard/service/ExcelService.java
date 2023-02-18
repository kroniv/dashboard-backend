package com.example.dashboard.service;

import com.example.dashboard.entities.Budget;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.example.dashboard.Config.PATH_TO_SAVE_FILE;
import static com.example.dashboard.service.FileUtils.*;
import static com.example.dashboard.service.FileUtils.getNumericValue;

@Service
public class ExcelService {

    public List<Budget> getBudgets(String fileName) throws IOException {
        File file = new File(PATH_TO_SAVE_FILE + fileName);
        FileInputStream fileStream = new FileInputStream(file);
        Workbook workbook = getWorkbook(fileStream, fileName);
        List<Row> rows = StreamSupport
                .stream(workbook.getSheetAt(0).spliterator(), false)
                .toList();
        List<Budget> result = new ArrayList<>();
        rows.stream()
                .skip(1)
                .filter(ROW_FILTER)
                .filter(row -> row.getCell(0) != null)
                .forEach(
                        row -> {
                            Budget budget = Budget.builder()
                                    .region(getStringValue(row.getCell(0)))
                                    .county(getStringValue(row.getCell(1)))
                                    .year(getNumericValue(row.getCell(2)).intValue())
                                    .direction(getStringValue(row.getCell(3)))
                                    .budgetSRF(getNumericValue(row.getCell(5)).longValue())
                                    .budgetMO(getNumericValue(row.getCell(6)).longValue())
                                    .grantNumber(getNumericValue(row.getCell(7)).intValue())
                                    .grantBudget(getNumericValue(row.getCell(8)).longValue())
                                    .population(getNumericValue(row.getCell(9)).longValue())
                                    .associationNumber(getNumericValue(row.getCell(9)).longValue())
                                    .build();
                            result.add(budget);
                        }
                );
        return result;
    }

    private Workbook getWorkbook(FileInputStream fileStream, String fileName) throws IOException {
        if (fileName.contains(".xlsx")) {
            return new XSSFWorkbook(fileStream);
        }
        return new HSSFWorkbook(fileStream);
    }
}
