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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;
    private final ItemService itemService;
    private final ProductService productService;

    public InvoiceServiceImpl(InvoiceRepository repository, InvoiceMapper mapper,
                              ItemService itemService, ProductService productService) {
        this.repository = repository;
        this.mapper = mapper;
        this.itemService = itemService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void create(@Valid InvoiceRequestDto dto) {
        Invoice invoice = mapper.dtoToEntity(dto);
        invoice.setDate(LocalDate.now());
        repository.save(invoice);

        List<ItemRequestDto> items = dto.getItems();
        for (ItemRequestDto item: items) {
            if (dto.getType().equals(TransactionType.INCOME)) {
                productService.income(item.getProductId(), item.getQuantity());
                itemService.create(item, invoice.getId(), productService.findById(item.getProductId()).getIncomePrice());
            }
            if (dto.getType().equals(TransactionType.OUTCOME)) {
                productService.outcome(item.getProductId(), item.getQuantity());
                itemService.create(item, invoice.getId(), productService.findById(item.getProductId()).getOutcomePrice());
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Invoice", "id = " + id));
        repository.delete(invoice);
    }

    @Override
    public InvoiceResponseDto findById(Long id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Invoice", "id = " + id)));
    }

    @Override
    public List<InvoiceResponseDto> findAll() {
        List<InvoiceResponseDto> list = mapper.entityToDto(repository.findAll());
        if (list.isEmpty())
            throw new NothingFoundException("Invoice", "all");
        return list;
    }

    @Override
    public List<InvoiceResponseDto> findAllByPartnerId(Long id) {
        List<InvoiceResponseDto> list = mapper.entityToDto(repository.findAllByPartnerId(id));
        if (list.isEmpty())
            throw new NothingFoundException("Invoice", "partner id = " + id);
        return list;
    }

    @Override
    public List<InvoiceResponseDto> findAllByWorkerId(Long id) {
        List<InvoiceResponseDto> list = mapper.entityToDto(repository.findAllByWorkerId(id));
        if (list.isEmpty())
            throw new NothingFoundException("Invoice", "worker id = " + id);
        return list;
    }

    @Override
    public List<InvoiceResponseDto> findAllByType(TransactionType type) {
        List<InvoiceResponseDto> list = mapper.entityToDto(repository.findAllByType(type));
        if (list.isEmpty())
            throw new NothingFoundException("Invoice", "type = " + type);
        return list;
    }

    @Override
    public List<InvoiceResponseDto> findAllByDate(LocalDate date) {
        List<InvoiceResponseDto> list = mapper.entityToDto(repository.findAllByDate(date));
        if (list.isEmpty())
            throw new NothingFoundException("Invoice", "date = " + date.toString());
        return list;
    }
}
