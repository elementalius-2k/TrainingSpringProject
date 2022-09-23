package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.ItemRequestDto;

public interface ItemService {
    void create(ItemRequestDto requestDto);
    void delete(Long id);

}
