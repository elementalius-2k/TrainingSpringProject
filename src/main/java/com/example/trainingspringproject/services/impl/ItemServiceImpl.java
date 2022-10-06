package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;
import com.example.trainingspringproject.models.entities.Item;
import com.example.trainingspringproject.models.mappers.ItemMapper;
import com.example.trainingspringproject.repositories.ItemRepository;
import com.example.trainingspringproject.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final ItemMapper mapper;

    @Override
    @Transactional
    public void create(@Valid ItemRequestDto requestDto, Long invoiceId, double price) {
        Item item = mapper.dtoToEntity(requestDto, invoiceId);
        item.setPrice(price);
        repository.save(item);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getByIdOrElseThrow(id));
    }

    @Override
    public List<ItemResponseDto> findAllByInvoiceId(Long invoiceId) {
        List<Item> list = repository.findAllByInvoiceId(invoiceId);
        checkEmptyList(list, "invoice id = " + invoiceId);
        return mapper.entityToDto(list);
    }

    private Item getByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Item", "id = " + id));
    }

    private void checkEmptyList(List<Item> list, String exceptionMessage) {
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Item", exceptionMessage);
    }
}
