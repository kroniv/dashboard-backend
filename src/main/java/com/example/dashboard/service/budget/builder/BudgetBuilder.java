package com.example.dashboard.service.budget.builder;

import com.example.dashboard.entities.Budget;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public interface BudgetBuilder {
    Budget build(List<Row> rows, Row row);

    BudgetBuilderType getType();
}
