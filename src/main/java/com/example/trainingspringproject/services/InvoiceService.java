package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.enums.TransactionType;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    void create(@Valid InvoiceRequestDto dto);
    void delete(Long id);
    InvoiceResponseDto findById(Long id);
    List<InvoiceResponseDto> findAll();
    List<InvoiceResponseDto> findAllByPartnerId(Long id);
    List<InvoiceResponseDto> findAllByWorkerId(Long id);
    List<InvoiceResponseDto> findAllByType(TransactionType type);
    List<InvoiceResponseDto> findAllByDate(LocalDate date);
}
