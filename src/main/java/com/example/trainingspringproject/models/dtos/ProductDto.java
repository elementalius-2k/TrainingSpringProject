package com.example.trainingspringproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private static final String NULL_ERR_MESSAGE = " in product is a required parameter.";
    private static final String BLANK_ERR_MESSAGE = " in product can't ba blank.";
    private static final String POSITIVE_ZERO_ERR_MESSAGE = " in product must be positive or zero.";
    private static final String POSITIVE_ERR_MESSAGE = " in product must be positive.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotNull(message = "Name" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Name" + BLANK_ERR_MESSAGE)
    private String name;

    private String description;

    @NotNull(message = "Group name" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Group name" + BLANK_ERR_MESSAGE)
    private String productGroupName;

    @NotNull(message = "Producer name" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Producer name" + BLANK_ERR_MESSAGE)
    private String producerName;

    @NotNull(message = "Product quantity" + NULL_ERR_MESSAGE)
    @PositiveOrZero(message = "Product quantity" + POSITIVE_ZERO_ERR_MESSAGE)
    private Integer quantity;

    @NotNull(message = "Product income price" + NULL_ERR_MESSAGE)
    @Positive(message = "Product income price" + POSITIVE_ERR_MESSAGE)
    private Double incomePrice;

    @NotNull(message = "Product outcome price" + NULL_ERR_MESSAGE)
    @Positive(message = "Product outcome price" + POSITIVE_ERR_MESSAGE)
    private Double outcomePrice;
}
