package com.example.trainingspringproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDto {
    private static final String NULL_ERR_MESSAGE = " in producer is a required parameter.";
    private static final String BLANK_ERR_MESSAGE = " in producer can't be blank.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotBlank(message = "Name" + BLANK_ERR_MESSAGE)
    private String name;

    @NotBlank(message = "Address" + BLANK_ERR_MESSAGE)
    private String address;
}
