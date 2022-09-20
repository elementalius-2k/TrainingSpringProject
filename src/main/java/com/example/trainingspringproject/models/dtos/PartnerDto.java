package com.example.trainingspringproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {
    private static final String NULL_ERR_MESSAGE = " in partner is a required parameter.";
    private static final String BLANK_ERR_MESSAGE = " in partner can't ba blank.";
    private static final String EMAIL_MESSAGE = " in partner must be a valid email address.";

    @NotNull(message = "ID" + NULL_ERR_MESSAGE)
    private Long id;

    @NotNull(message = "Name" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Name" + BLANK_ERR_MESSAGE)
    private String name;

    @NotNull(message = "Address" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Address" + BLANK_ERR_MESSAGE)
    private String address;

    @NotNull(message = "Email" + NULL_ERR_MESSAGE)
    @Email(message = "Email" + EMAIL_MESSAGE)
    private String email;

    @NotNull(message = "Requisites" + NULL_ERR_MESSAGE)
    @NotBlank(message = "Requisites" + BLANK_ERR_MESSAGE)
    private String requisites;
}
