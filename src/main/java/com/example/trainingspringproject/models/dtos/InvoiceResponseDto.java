package com.example.trainingspringproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponseDto {
    private static final String NULL_ERR_MESSAGE = " in invoice is a required parameter.";
    private static final String BLANK_ERR_MESSAGE = " in invoice can't be blank.";
    private static final String TYPE_ERR_MESSAGE = "Invoice transaction type must be 'income' or 'outcome'.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotNull(message = "Partner name" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Partner name" + BLANK_ERR_MESSAGE)
    private String partnerName;

    @NotNull(message = "Worker name" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Worker name" + BLANK_ERR_MESSAGE)
    private String workerName;

    @NotNull(message = "Transaction type" + NULL_ERR_MESSAGE)
    @Pattern(regexp = "income|outcome", message = TYPE_ERR_MESSAGE)
    private String type;

    @NotNull(message = "Date" + NULL_ERR_MESSAGE)
    private LocalDate date;

    @NotNull(message = "Items list" + NULL_ERR_MESSAGE)
    private List<ItemResponseDto> items;
}