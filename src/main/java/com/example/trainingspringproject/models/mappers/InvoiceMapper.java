package com.example.trainingspringproject.models.mappers;

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

@Mapper(componentModel = "spring", uses = { ItemMapper.class })
public abstract class InvoiceMapper {
    @Autowired
    protected PartnerRepository partnerRepository;
    @Autowired
    protected WorkerRepository workerRepository;

    @Mapping(target = "partner", expression = "java(partnerRepository.findById(dto.getPartnerId()).get())")
    @Mapping(target = "worker", expression = "java(workerRepository.findById(dto.getWorkerId()).get())")
    public abstract Invoice dtoToEntity(InvoiceRequestDto dto);
    @Mapping(target = "partnerName", expression = "java(entity.getPartner().getName())")
    @Mapping(target = "workerName", expression = "java(entity.getWorker().getName())")
    public abstract InvoiceResponseDto entityToDto(Invoice entity);

    public abstract List<Invoice> dtoToEntity(Collection<InvoiceRequestDto> dto);
    public abstract List<InvoiceResponseDto> entityToDto(Collection<Invoice> entity);
}
