package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.WorkerDto;
import com.example.trainingspringproject.models.entities.Worker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    Worker dtoToEntity(WorkerDto dto);
    WorkerDto entityToDto(Worker entity);

    List<Worker> dtoToEntity(Iterable<WorkerDto> dto);
    List<WorkerDto> entityToDto(Iterable<Worker> entity);
}
