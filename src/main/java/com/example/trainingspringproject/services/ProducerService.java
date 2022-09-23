package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.ProducerDto;

import java.util.List;

public interface ProducerService extends BaseService<ProducerDto> {
    ProducerDto findByName(String name);
    List<ProducerDto> findAllByAddressLike(String address);
}
