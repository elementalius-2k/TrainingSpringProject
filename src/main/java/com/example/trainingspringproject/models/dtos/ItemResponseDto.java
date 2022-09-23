package com.example.trainingspringproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private static final String NULL_ERR_MESSAGE = " in item is a required parameter.";
    private static final String POSITIVE_ERR_MESSAGE = " in item must be positive.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotNull(message = "Product ID" + NULL_ERR_MESSAGE)
    private Long productId;

    @NotNull(message = "Quantity" + NULL_ERR_MESSAGE)
    @Positive(message = "Quantity" + POSITIVE_ERR_MESSAGE)
    private Integer quantity;

    @NotNull(message = "Price" + NULL_ERR_MESSAGE)
    @Positive(message = "Price" + POSITIVE_ERR_MESSAGE)
    private Double price;
}
