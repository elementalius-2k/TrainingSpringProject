package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;

import javax.validation.Valid;
import java.util.List;

public interface ItemService {
    void create(@Valid ItemRequestDto requestDto, Long invoiceId, double price);
    void delete(Long id);
    List<ItemResponseDto> findAllByInvoiceId(Long invoiceId);
}
