package com.example.trainingspringproject.models.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {
    private static final String NULL_ERR_MESSAGE = " in worker is a required parameter.";
    private static final String BLANK_ERR_MESSAGE = " in worker can't be blank.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotBlank(message = "Name" + BLANK_ERR_MESSAGE)
    private String name;

    @NotBlank(message = "Job" + BLANK_ERR_MESSAGE)
    private String job;
}
