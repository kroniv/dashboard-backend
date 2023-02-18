package com.example.dashboard.service.budget.builder;

import com.example.dashboard.entities.Budget;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.dashboard.service.ExcelUtils.getNumericValue;
import static com.example.dashboard.service.ExcelUtils.getStringValue;

@Component
public class CommonBudgetBuilder implements BudgetBuilder {

    @Override
    public Budget build(List<Row> rows, Row row) {
        return Budget.builder()
                .region(getStringValue(row.getCell(0)))
                .county(getStringValue(row.getCell(1)))
                .year(getNumericValue(row.getCell(2)).intValue())
                .direction(getStringValue(row.getCell(3)))
                .budgetSRF(getNumericValue(row.getCell(5)).longValue())
                .budgetMO(getNumericValue(row.getCell(6)).longValue())
                .grantNumber(getNumericValue(row.getCell(7)).intValue())
                .grantBudget(getNumericValue(row.getCell(8)).longValue())
                .population(getNumericValue(row.getCell(9)).longValue())
                .associationNumber(getNumericValue(row.getCell(10)).longValue())
                .build();
    }

    @Override
    public BudgetBuilderType getType() {
        return BudgetBuilderType.COMMON;
    }
}
