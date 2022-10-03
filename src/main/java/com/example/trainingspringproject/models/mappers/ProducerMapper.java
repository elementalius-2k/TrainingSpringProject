package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.ProducerDto;
import com.example.trainingspringproject.models.entities.Producer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProducerMapper {
    Producer dtoToEntity(ProducerDto dto);
    ProducerDto entityToDto(Producer entity);

    List<Producer> dtoToEntity(Iterable<ProducerDto> dto);
    List<ProducerDto> entityToDto(Iterable<Producer> entity);
}
