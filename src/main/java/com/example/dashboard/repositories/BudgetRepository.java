package com.example.dashboard.repositories;

import com.example.dashboard.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, String> {
}
