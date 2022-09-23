package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.entities.Invoice;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice dtoToEntity(InvoiceRequestDto dto);
    InvoiceResponseDto entityToDto(Invoice entity);

    List<Invoice> dtoToEntity(Collection<InvoiceRequestDto> dto);
    List<InvoiceResponseDto> entityToDto(Collection<Invoice> entity);
}
