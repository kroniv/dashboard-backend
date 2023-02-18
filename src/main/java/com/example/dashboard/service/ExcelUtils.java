package com.example.dashboard.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.function.Predicate;

public class ExcelUtils {

    private static final String EMPTY_STRING = "";

    private static final List<String> UNUSED_ROW = List.of(
            "биомедицина",
            "промышленные технологии\n      (нанотехнологии, композиты, пр.)",
            "аэрокосмос",
            "сельское хозяйство",
            "информационные технологии - IT\n      (в том числе робототехника)",
            "другое",
            "на развитие и мероприятия\n      военно-патриотических клубов",
            "поисковых отрядов и объединений",
            "историко-краеведческих кружков и объединений исторической реконструкции",
            "молодeжных казачьих объединений",
            "волонтeрских объединений в сфере\n      патриотического воспитания детей\n      и молодeжи",
            "другие"
    );

    public static Predicate<Row> ROW_FILTER =
            row -> UNUSED_ROW.stream().noneMatch(unused -> getStringValue(row.getCell(3)).contains(unused));

    public static String getStringValue(Cell cell) {
        if (cell == null) {
            return EMPTY_STRING;
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> EMPTY_STRING;
        };
    }

    public static Double getNumericValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        if (!CellType.NUMERIC.equals(cell.getCellType())) {
            return 0.0;
        }
        return cell.getNumericCellValue();
    }

    public static long getLongSum(List<Row> rows, int valueIdx, int startIdx, int count) {
        long result = 0;
        for (int i = startIdx; i < startIdx + count; i++) {
            result += getNumericValue(rows.get(i).getCell(valueIdx)).longValue();
        }
        return result;
    }

    public static int getIntSum(List<Row> rows, int valueIdx, int startIdx, int count) {
        int result = 0;
        for (int i = startIdx; i < startIdx + count; i++) {
            result += getNumericValue(rows.get(i).getCell(valueIdx)).intValue();
        }
        return result;
    }
}
