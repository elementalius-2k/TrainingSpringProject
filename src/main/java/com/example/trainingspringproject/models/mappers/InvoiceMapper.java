package com.example.trainingspringproject.models.mappers;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.entities.Invoice;
import com.example.trainingspringproject.repositories.PartnerRepository;
import com.example.trainingspringproject.repositories.WorkerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InvoiceMapper {
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private ItemMapper itemMapper;

    public Invoice dtoToEntity(InvoiceRequestDto dto) {
        Invoice invoice = new Invoice();
        invoice.setType(dto.getType());
        invoice.setItems(itemMapper.dtoToEntity(dto.getItems()));
        invoice.setWorker(workerRepository.findById(dto.getWorkerId())
                .orElseThrow(() -> new NothingFoundException("Worker", "id = " + dto.getWorkerId())));
        invoice.setPartner(partnerRepository.findById(dto.getPartnerId())
                .orElseThrow(() -> new NothingFoundException("Partner", "id = " + dto.getPartnerId())));
        return invoice;
    }
    @Mapping(target = "partnerName", expression = "java(entity.getPartner().getName())")
    @Mapping(target = "workerName", expression = "java(entity.getWorker().getName())")
    public abstract InvoiceResponseDto entityToDto(Invoice entity);

    public abstract List<Invoice> dtoToEntity(Collection<InvoiceRequestDto> dto);
    public abstract List<InvoiceResponseDto> entityToDto(Collection<Invoice> entity);
}
