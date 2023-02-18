package com.example.dashboard.service.budget;

import com.example.dashboard.dto.BudgetCrumbDto;
import com.example.dashboard.dto.BudgetDto;
import com.example.dashboard.dto.BudgetDtoWithCrumbs;
import com.example.dashboard.entities.Budget;
import com.example.dashboard.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    public List<BudgetDtoWithCrumbs> getAllBudgets() {
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
                .map(this::toCrumbDto)
                .collect(Collectors.toList());
    }

    public BudgetDto getBudget(String region, int year, String direction) {
        Optional<Budget> findBudget = budgetRepository.findByRegionAndYearAndDirection(region, year, direction);
        Budget budget = findBudget.orElseThrow(() -> new RuntimeException("No match in budgets"));
        return toDto(budget);
    }

    public void createOrUpdateBudget(BudgetDto dto) {
        Optional<Budget> findBudget = budgetRepository.findByRegionAndYearAndDirection(dto.getRegion(), dto.getYear(), dto.getDirection());
        if (findBudget.isPresent()) {
            Budget budgetToUpdate = findBudget.get();
            updateBudget(budgetToUpdate, dto);
            budgetRepository.save(budgetToUpdate);
        } else {
            budgetRepository.save(toEntity(dto));
        }
    }

    public void delete(String region, int year, String direction) {
        budgetRepository
                .findByRegionAndYearAndDirection(region, year, direction)
                .ifPresent(budget -> budgetRepository.delete(budget));
    }

    private BudgetDtoWithCrumbs toCrumbDto(List<Budget> budgets) {
        Budget budget = budgets.get(0);
        return new BudgetDtoWithCrumbs(
                budget.getRegion(),
                budget.getCounty(),
                budget.getYear(),
                budgets.stream().mapToLong(Budget::getBudgetSRF).sum(),
                budgets.stream().mapToLong(Budget::getBudgetMO).sum(),
                budgets.stream().mapToInt(Budget::getGrantNumber).sum(),
                budgets.stream().mapToLong(Budget::getGrantBudget).sum(),
                budgets.stream().mapToLong(Budget::getPopulation).sum(),
                budgets.stream().mapToLong(Budget::getAssociationNumber).sum(),
                budgets.stream().map(this::toCrumbDto).collect(Collectors.toList()));
    }

    private BudgetCrumbDto toCrumbDto(Budget budget) {
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

    private BudgetDto toDto(Budget budget) {
        return new BudgetDto(
                budget.getRegion(),
                budget.getCounty(),
                budget.getYear(),
                budget.getDirection(),
                budget.getBudgetSRF(),
                budget.getBudgetMO(),
                budget.getGrantNumber(),
                budget.getGrantBudget(),
                budget.getPopulation(),
                budget.getAssociationNumber()
        );
    }

    private Budget toEntity(BudgetDto dto) {
        return new Budget(
                dto.getRegion(),
                dto.getCounty(),
                dto.getYear(),
                dto.getDirection(),
                dto.getBudgetSRF(),
                dto.getBudgetMO(),
                dto.getGrantNumber(),
                dto.getGrantBudget(),
                dto.getPopulation(),
                dto.getAssociationNumber()
        );
    }

    private void updateBudget(Budget budgetToUpdate, BudgetDto newBudget) {
        budgetToUpdate.setBudgetSRF(newBudget.getBudgetSRF());
        budgetToUpdate.setBudgetMO(newBudget.getBudgetMO());
        budgetToUpdate.setGrantNumber(newBudget.getGrantNumber());
        budgetToUpdate.setGrantBudget(newBudget.getGrantBudget());
        budgetToUpdate.setPopulation(newBudget.getPopulation());
        budgetToUpdate.setAssociationNumber(newBudget.getAssociationNumber());
    }
}
