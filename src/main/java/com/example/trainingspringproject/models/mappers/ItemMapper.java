package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;
import com.example.trainingspringproject.models.entities.Item;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item dtoToEntity(ItemRequestDto dto);
    ItemResponseDto entityToDto(Item entity);

    List<Item> dtoToEntity(Collection<ItemRequestDto> dto);
    List<ItemResponseDto> entityToDto(Collection<Item> entity);
}
