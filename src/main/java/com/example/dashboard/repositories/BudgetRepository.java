package com.example.dashboard.repositories;

import com.example.dashboard.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByRegionAndYearAndDirection(String region, int year, String direction);
}
