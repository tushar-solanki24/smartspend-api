package com.smartspend.smartspend_api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    private Double amount;
    private String category;
    private LocalDate date;
    private String description;
}
