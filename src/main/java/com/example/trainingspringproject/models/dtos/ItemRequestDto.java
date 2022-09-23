package com.example.trainingspringproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private static final String NULL_ERR_MESSAGE = " in item is a required parameter.";
    private static final String POSITIVE_ERR_MESSAGE = " in item must be positive.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotNull(message = "Invoice ID" + NULL_ERR_MESSAGE)
    private Long invoiceId;

    @NotNull(message = "Product ID" + NULL_ERR_MESSAGE)
    private Long productId;

    @NotNull(message = "Quantity" + NULL_ERR_MESSAGE)
    @Positive(message = "Quantity" + POSITIVE_ERR_MESSAGE)
    private Integer quantity;
}
