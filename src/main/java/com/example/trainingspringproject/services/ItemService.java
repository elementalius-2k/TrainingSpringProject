package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.ItemRequestDto;

import javax.validation.Valid;

public interface ItemService {
    void create(@Valid ItemRequestDto requestDto, double price);
    void delete(Long id);

}
