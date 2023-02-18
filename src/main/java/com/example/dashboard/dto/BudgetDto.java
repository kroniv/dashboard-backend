package com.example.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetDto {

    private String region;

    private String county;

    private int year;

    private long budgetSRF;

    private long budgetMO;

    private int grantNumber;

    private long grantBudget;

    private long population;

    private long associationNumber;

    private List<BudgetCrumbDto> budgetCrumbs;
}
