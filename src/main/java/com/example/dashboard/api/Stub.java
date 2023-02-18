package com.example.dashboard.api;

import com.example.dashboard.dto.BudgetDtoStub;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stub")
public class Stub {

    @CrossOrigin
    @GetMapping
    public BudgetDtoStub getStubbedBudget() {
        return new BudgetDtoStub(1, "Алтайский край", 28839390, 13755755, 43, 1572066, 549835, 3498);
    }

}
