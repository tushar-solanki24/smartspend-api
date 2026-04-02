package com.smartspend.smartspend_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {

    private Double amount;
    private String category;
    private int month;
    private int year;
}
