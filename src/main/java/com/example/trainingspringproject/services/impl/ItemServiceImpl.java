package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;
import com.example.trainingspringproject.models.entities.Item;
import com.example.trainingspringproject.models.mappers.ItemMapper;
import com.example.trainingspringproject.repositories.ItemRepository;
import com.example.trainingspringproject.services.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final ItemMapper mapper;

    public ItemServiceImpl(ItemRepository repository, ItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void create(@Valid ItemRequestDto requestDto, Long invoiceId, double price) {
        Item item = mapper.dtoToEntity(requestDto, invoiceId);
        item.setPrice(price);
        repository.save(item);
    }

    @Override
    public void delete(Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Item", "id = " + id));
        repository.delete(item);
    }

    @Override
    public List<ItemResponseDto> findAllByInvoiceId(Long invoiceId) {
        List<ItemResponseDto> list = mapper.entityToDto(repository.findAllByInvoiceId(invoiceId));
        if (list.isEmpty())
            throw new NothingFoundException("Item", "invoice id = " + invoiceId);
        return list;
    }
}
