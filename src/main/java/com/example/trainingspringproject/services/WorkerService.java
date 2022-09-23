package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.WorkerDto;

import java.util.List;

public interface WorkerService extends BaseService<WorkerDto> {
    WorkerDto findByName(String name);
    List<WorkerDto> findAllByJob(String job);
}
