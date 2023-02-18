package com.example.dashboard.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BudgetDtoStub implements Serializable {

    long id;
    String region_name;
    long budgetRSF;
    long budgetMO;
    int count_grant;
    long budget_grant;
    long number_young;
    long number_public_associations;
}
