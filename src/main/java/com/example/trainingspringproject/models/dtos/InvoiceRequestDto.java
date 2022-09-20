package com.example.trainingspringproject.models.dtos;

import com.example.trainingspringproject.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequestDto {
    private static final String NULL_ERR_MESSAGE = " in invoice is a required parameter.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotNull(message = "Partner ID" + NULL_ERR_MESSAGE)
    private Long partnerId;

    @NotNull(message = "Worker ID" + NULL_ERR_MESSAGE)
    private Long workerId;

    @NotNull(message = "Transaction type" + NULL_ERR_MESSAGE)
    private TransactionType type;

    @NotNull(message = "Items list" + NULL_ERR_MESSAGE)
    private List<ItemRequestDto> items;
}
