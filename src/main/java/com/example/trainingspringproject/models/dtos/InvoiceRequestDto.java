package com.example.trainingspringproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequestDto {
    private static final String NULL_ERR_MESSAGE = " in invoice is a required parameter.";
    private static final String TYPE_ERR_MESSAGE = "Invoice transaction type must be 'income' or 'outcome'.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotNull(message = "Partner ID" + NULL_ERR_MESSAGE)
    private Long partnerId;

    @NotNull(message = "Worker ID" + NULL_ERR_MESSAGE)
    private Long workerId;

    @NotNull(message = "Transaction type" + NULL_ERR_MESSAGE)
    @Pattern(regexp = "income|outcome", message = TYPE_ERR_MESSAGE)
    private String type;

    @NotNull(message = "Items list" + NULL_ERR_MESSAGE)
    private List<ItemRequestDto> items;
}
