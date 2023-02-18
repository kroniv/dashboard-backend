package com.example.dashboard.api;

import com.example.dashboard.dto.BudgetDto;
import com.example.dashboard.service.budget.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<BudgetDto>> getBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }
}
