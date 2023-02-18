package com.example.dashboard.service.budget;

import com.example.dashboard.dto.BudgetCrumbDto;
import com.example.dashboard.dto.BudgetDto;
import com.example.dashboard.entities.Budget;
import com.example.dashboard.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    public List<BudgetDto> getAllBudgets() {
        List<Budget> allBudgets = budgetRepository.findAll();
        Map<Pair<String, Integer>, List<Budget>> groupedBudgets =
                allBudgets
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        budget -> Pair.of(budget.getRegion(), budget.getYear()),
                                        Collectors.toList()));
        return groupedBudgets.values()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BudgetDto toDto(List<Budget> budgets) {
        Budget budget = budgets.get(0);
        return new BudgetDto(
                budget.getRegion(),
                budget.getCounty(),
                budget.getYear(),
                budgets.stream().mapToLong(Budget::getBudgetSRF).sum(),
                budgets.stream().mapToLong(Budget::getBudgetMO).sum(),
                budgets.stream().mapToInt(Budget::getGrantNumber).sum(),
                budgets.stream().mapToLong(Budget::getGrantBudget).sum(),
                budgets.stream().mapToLong(Budget::getPopulation).sum(),
                budgets.stream().mapToLong(Budget::getAssociationNumber).sum(),
                budgets.stream().map(this::toDto).collect(Collectors.toList()));
    }

    private BudgetCrumbDto toDto(Budget budget) {
        return new BudgetCrumbDto(
                budget.getDirection(),
                budget.getBudgetSRF(),
                budget.getBudgetMO(),
                budget.getGrantNumber(),
                budget.getGrantBudget(),
                budget.getPopulation(),
                budget.getAssociationNumber()
        );
    }
}
