package com.example.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetCrumbDto {

    private String direction;

    private long budgetSRF;

    private long budgetMO;

    private int grantNumber;

    private long grantBudget;

    private long population;

    private long associationNumber;
}
