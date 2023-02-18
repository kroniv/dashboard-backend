package com.example.dashboard.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String region;

    private String county;

    private int year;

    private String direction;

    private long budgetSRF;

    private long budgetMO;

    private int grantNumber;

    private long grantBudget;

    private long population;

    private long associationNumber;

    public Budget(String region, String county, int year, String direction, long budgetSRF, long budgetMO, int grantNumber, long grantBudget, long population, long associationNumber) {
        this.region = region;
        this.county = county;
        this.year = year;
        this.direction = direction;
        this.budgetSRF = budgetSRF;
        this.budgetMO = budgetMO;
        this.grantNumber = grantNumber;
        this.grantBudget = grantBudget;
        this.population = population;
        this.associationNumber = associationNumber;
    }
}
