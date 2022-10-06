package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.entities.Invoice;
import com.example.trainingspringproject.models.enums.TransactionType;
import com.example.trainingspringproject.models.mappers.InvoiceMapper;
import com.example.trainingspringproject.repositories.InvoiceRepository;
import com.example.trainingspringproject.services.InvoiceService;
import com.example.trainingspringproject.services.ItemService;
import com.example.trainingspringproject.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;
    private final ItemService itemService;
    private final ProductService productService;

    @Override
    @Transactional
    public void create(@Valid InvoiceRequestDto dto) {
        Invoice invoice = mapper.dtoToEntity(dto);
        invoice.setDate(LocalDate.now());
        repository.save(invoice);

        List<ItemRequestDto> items = dto.getItems();
        for (ItemRequestDto item: items) {
            switch (dto.getType()) {
                case INCOME:
                    productService.income(item.getProductId(), item.getQuantity());
                    itemService.create(item, invoice.getId(), productService.findById(item.getProductId()).getIncomePrice());
                    break;
                case OUTCOME:
                    productService.outcome(item.getProductId(), item.getQuantity());
                    itemService.create(item, invoice.getId(), productService.findById(item.getProductId()).getOutcomePrice());
                    break;
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(getByIdOrElseThrow(id));
    }

    @Override
    public InvoiceResponseDto findById(Long id) {
        return mapper.entityToDto(getByIdOrElseThrow(id));
    }

    @Override
    public List<InvoiceResponseDto> findAll() {
        List<Invoice> list = (List<Invoice>) repository.findAll();
        checkEmptyList(list, "all");
        return mapper.entityToDto(list);
    }

    @Override
    public List<InvoiceResponseDto> findAllByPartnerId(Long id) {
        List<Invoice> list = repository.findAllByPartnerId(id);
        checkEmptyList(list, "partner id = " + id);
        return mapper.entityToDto(list);
    }

    @Override
    public List<InvoiceResponseDto> findAllByWorkerId(Long id) {
        List<Invoice> list = repository.findAllByWorkerId(id);
        checkEmptyList(list, "worker id = " + id);
        return mapper.entityToDto(list);
    }

    @Override
    public List<InvoiceResponseDto> findAllByType(TransactionType type) {
        List<Invoice> list = repository.findAllByType(type);
        checkEmptyList(list, "type = " + type);
        return mapper.entityToDto(list);
    }

    @Override
    public List<InvoiceResponseDto> findAllByDate(LocalDate date) {
        List<Invoice> list = repository.findAllByDate(date);
        checkEmptyList(list, "date = " + date.toString());
        return mapper.entityToDto(list);
    }

    private Invoice getByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Invoice", "id = " + id));
    }

    private void checkEmptyList(List<Invoice> list, String exceptionMessage) {
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Invoice", exceptionMessage);
    }
}
