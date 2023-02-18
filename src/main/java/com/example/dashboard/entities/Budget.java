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
}
