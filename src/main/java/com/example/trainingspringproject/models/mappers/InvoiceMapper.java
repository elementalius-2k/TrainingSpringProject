package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;
import com.example.trainingspringproject.models.entities.Invoice;
import com.example.trainingspringproject.repositories.ItemRepository;
import com.example.trainingspringproject.repositories.PartnerRepository;
import com.example.trainingspringproject.repositories.WorkerRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public abstract class InvoiceMapper {
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;

    public Invoice dtoToEntity(InvoiceRequestDto dto) {
        Invoice entity = new Invoice();
        entity.setType(dto.getType());
        entity.setWorker(workerRepository.findById(dto.getWorkerId())
                .orElseThrow(() -> new NothingFoundException("Worker", "id = " + dto.getWorkerId())));
        entity.setPartner(partnerRepository.findById(dto.getPartnerId())
                .orElseThrow(() -> new NothingFoundException("Partner", "id = " + dto.getPartnerId())));
        return entity;
    }

    public InvoiceResponseDto entityToDto(Invoice entity) {
        InvoiceResponseDto dto = new InvoiceResponseDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setDate(entity.getDate());
        dto.setPartnerName(entity.getPartner().getName());
        dto.setWorkerName(entity.getWorker().getName());
        List<ItemResponseDto> list = itemMapper.entityToDto(itemRepository.findAllByInvoiceId(entity.getId()));
        if (list.isEmpty())
            throw new NothingFoundException("Item", "invoice id = " + entity.getId());
        dto.setItems(list);
        return dto;
    }

    public abstract List<Invoice> dtoToEntity(Collection<InvoiceRequestDto> dto);
    public abstract List<InvoiceResponseDto> entityToDto(Collection<Invoice> entity);
}
