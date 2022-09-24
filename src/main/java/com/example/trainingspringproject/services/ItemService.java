package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.ItemRequestDto;

public interface ItemService {
    void create(ItemRequestDto requestDto, double price);
    void delete(Long id);

}
