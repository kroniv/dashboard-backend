package com.example.dashboard.service;

import com.example.dashboard.entities.Budget;
import com.example.dashboard.repositories.BudgetRepository;
import com.example.dashboard.service.budget.builder.BudgetBuilder;
import com.example.dashboard.service.budget.builder.BudgetBuilderType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.dashboard.service.ExcelUtils.ROW_FILTER;
import static com.example.dashboard.service.ExcelUtils.getStringValue;

@Service
public class ExcelService {

    private static final String TECHNICAL = "Вовлечение молодeжи в инновационную деятельность и научно-техническое творчество";
    private static final String MILITARY = "Патриотическое воспитание молодeжи";
    private final BudgetRepository budgetRepository;
    private final Map<BudgetBuilderType, BudgetBuilder> budgetBuilders;

    public ExcelService(BudgetRepository budgetRepository, List<BudgetBuilder> budgetBuilders) {
        this.budgetRepository = budgetRepository;
        this.budgetBuilders = budgetBuilders.stream().collect(Collectors.toMap(BudgetBuilder::getType, Function.identity()));
    }


    public List<Budget> getBudgets(String fileName) throws IOException {
        File file = new File(fileName);
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
                            Budget budget = createBudget(rows, row);
                            result.add(budget);
                        }
                );
        return result;
    }

    private Budget createBudget(List<Row> rows, Row row) {
        if (getStringValue(row.getCell(3)).contains(TECHNICAL)) {
            Budget budget = budgetBuilders.get(BudgetBuilderType.SUMMARIZING).build(rows, row);
            budget.setDirection(TECHNICAL);
            return budget;
        } else if (getStringValue(row.getCell(3)).contains(MILITARY)) {
            Budget budget = budgetBuilders.get(BudgetBuilderType.SUMMARIZING).build(rows, row);
            budget.setDirection(MILITARY);
            return budget;
        }
        return budgetBuilders.get(BudgetBuilderType.COMMON).build(rows, row);
    }

    public void saveBudgets(String fileName) throws IOException {
        List<Budget> budgets = getBudgets(fileName);
        budgets.forEach(budget -> {
            Optional<Budget> findBudget =
                    budgetRepository.findByRegionAndYearAndDirection(
                            budget.getRegion(),
                            budget.getYear(),
                            budget.getDirection());
            if (findBudget.isPresent()) {
                Budget budgetToUpdate = findBudget.get();
                updateBudget(budgetToUpdate, budget);
                budgetRepository.save(budgetToUpdate);
            } else {
                budgetRepository.save(budget);
            }
        });
    }

    private Workbook getWorkbook(FileInputStream fileStream, String fileName) throws IOException {
        if (fileName.contains(".xlsx")) {
            return new XSSFWorkbook(fileStream);
        }
        return new HSSFWorkbook(fileStream);
    }

    private void updateBudget(Budget budgetToUpdate, Budget newBudget) {
        budgetToUpdate.setBudgetSRF(newBudget.getBudgetSRF());
        budgetToUpdate.setBudgetMO(newBudget.getBudgetMO());
        budgetToUpdate.setGrantNumber(newBudget.getGrantNumber());
        budgetToUpdate.setGrantBudget(newBudget.getGrantBudget());
        budgetToUpdate.setPopulation(newBudget.getPopulation());
        budgetToUpdate.setAssociationNumber(newBudget.getAssociationNumber());
    }
}
