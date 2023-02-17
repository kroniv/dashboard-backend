package com.example.dashboard.service;

import static com.example.dashboard.Config.PATH_TO_SAVE_FILE;

import com.example.dashboard.dto.ExcelRow;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class FileService {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String EMPTY_STRING = "";

  public String getSerializedFile(String fileName) throws IOException {
    File file = new File(PATH_TO_SAVE_FILE + fileName);
    FileInputStream fileStream = new FileInputStream(file);
    Workbook workbook = getWorkbook(fileStream, fileName);
    List<Row> rows = StreamSupport
        .stream(workbook.getSheetAt(0).spliterator(), false)
        .toList();
    List<String> keys = StreamSupport
        .stream(rows.get(0).spliterator(), false)
        .map(Cell::getStringCellValue)
        .toList();
    List<ExcelRow> result = new ArrayList<>();
    rows.stream().skip(1).forEach(
        row -> {
          ExcelRow excelRow = new ExcelRow();
          excelRow.setKey(getStringValue(row.getCell(0)));
          excelRow.setKeyName(keys.get(0));
          StreamSupport
              .stream(row.spliterator(), false)
              .skip(1)
              .forEach(cell -> excelRow.put(keys.get(cell.getColumnIndex()), getStringValue(cell)));
          result.add(excelRow);
        }
    );
    return mapper.writeValueAsString(result);
  }

  private Workbook getWorkbook(FileInputStream fileStream, String fileName) throws IOException {
    if (fileName.contains(".xlsx")) {
      return new XSSFWorkbook(fileStream);
    }
    return new HSSFWorkbook(fileStream);
  }

  private String getStringValue(Cell cell) {
    return switch (cell.getCellType()) {
      case STRING -> cell.getStringCellValue();
      case NUMERIC -> String.valueOf(cell.getNumericCellValue());
      case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
      default -> EMPTY_STRING;
    };
  }
}
