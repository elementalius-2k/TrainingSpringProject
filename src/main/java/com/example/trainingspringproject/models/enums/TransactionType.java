package com.example.trainingspringproject.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType {
    INCOME("income"),
    OUTCOME("outcome");

    private final String type;
}
