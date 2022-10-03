package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.PartnerDto;
import com.example.trainingspringproject.models.entities.Partner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    Partner dtoToEntity(PartnerDto dto);
    PartnerDto entityToDto(Partner entity);

    List<Partner> dtoToEntity(Iterable<PartnerDto> dto);
    List<PartnerDto> entityToDto(Iterable<Partner> entity);
}
