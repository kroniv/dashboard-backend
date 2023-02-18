package com.example.dashboard.api;

import com.example.dashboard.dto.BudgetDto;
import com.example.dashboard.dto.BudgetDtoRequest;
import com.example.dashboard.dto.BudgetDtoWithCrumbs;
import com.example.dashboard.service.budget.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<BudgetDtoWithCrumbs>> getBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<BudgetDto> getBudget(BudgetDtoRequest request) {
        return ResponseEntity.ok(budgetService.getBudget(request.getRegion(), request.getYear(), request.getDirection()));
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Void> postBudget(@RequestBody BudgetDto dto) {
        budgetService.createOrUpdateBudget(dto);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<Void> deleteBudget(BudgetDtoRequest request) {
        budgetService.delete(request.getRegion(), request.getYear(), request.getDirection());
        return ResponseEntity.noContent().build();
    }
}
