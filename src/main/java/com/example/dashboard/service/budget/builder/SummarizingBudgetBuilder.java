package com.example.dashboard.service.budget.builder;

import com.example.dashboard.entities.Budget;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.dashboard.service.ExcelUtils.*;

@Component
public class SummarizingBudgetBuilder implements BudgetBuilder {

    private static final int COUNT = 6;

    @Override
    public Budget build(List<Row> rows, Row row) {
        return Budget.builder()
                .region(getStringValue(row.getCell(0)))
                .county(getStringValue(row.getCell(1)))
                .year(getNumericValue(row.getCell(2)).intValue())
                .direction(getStringValue(row.getCell(3)))
                .budgetSRF(getLongSum(rows, 5, row.getRowNum() + 1, COUNT))
                .budgetMO(getLongSum(rows, 6, row.getRowNum() + 1, COUNT))
                .grantNumber(getIntSum(rows, 7, row.getRowNum() + 1, COUNT))
                .grantBudget(getLongSum(rows, 8, row.getRowNum() + 1, COUNT))
                .population(getLongSum(rows, 9, row.getRowNum() + 1, COUNT))
                .associationNumber(getLongSum(rows, 10, row.getRowNum() + 1, COUNT))
                .build();
    }

    @Override
    public BudgetBuilderType getType() {
        return BudgetBuilderType.SUMMARIZING;
    }
}
