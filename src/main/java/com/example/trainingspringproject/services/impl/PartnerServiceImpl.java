package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.PartnerDto;
import com.example.trainingspringproject.models.entities.Partner;
import com.example.trainingspringproject.models.mappers.PartnerMapper;
import com.example.trainingspringproject.repositories.PartnerRepository;
import com.example.trainingspringproject.services.PartnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository repository;
    private final PartnerMapper mapper;

    public PartnerServiceImpl(PartnerRepository repository, PartnerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void create(@Valid PartnerDto dto) {
        //проверка нарушения уникальности name
        if (repository.findByName(dto.getName()).isPresent())
            throw new AlreadyExistsException("Partner with name " + dto.getName());
        //проверка нарушения уникальности requisites
        if (repository.findByRequisites(dto.getRequisites()).isPresent())
            throw new AlreadyExistsException("Partner with requisites " + dto.getRequisites());

        Partner partner = mapper.dtoToEntity(dto);
        partner.setId(null);
        repository.save(partner);
    }

    @Override
    @Transactional
    public void update(@Valid PartnerDto dto) {
        //проверка существования записи с нужным id
        Partner oldData = repository.findById(dto.getId())
                .orElseThrow(() -> new NothingFoundException("Partner", "id = " + dto.getId()));
        //если произошло изменение поля name, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getName().equals(oldData.getName()) && repository.findByName(dto.getName()).isPresent())
            throw new AlreadyExistsException("Partner with name " + dto.getName());
        //если произошло изменение поля requisites, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getRequisites().equals(oldData.getRequisites()) && repository.findByRequisites(dto.getRequisites()).isPresent())
            throw new AlreadyExistsException("Partner with requisites " + dto.getRequisites());

        Partner newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        Partner partner = repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Partner", "id = " + id));
        repository.delete(partner);
    }

    @Override
    public PartnerDto findById(Long id) {
        return mapper.entityToDto(repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Partner", "id = " + id)));
    }

    @Override
    public List<PartnerDto> findAll() {
        List<PartnerDto> list = mapper.entityToDto(repository.findAll());
        if (list.isEmpty())
            throw new NothingFoundException("Partner", "all");
        return list;
    }

    @Override
    public PartnerDto findByName(String name) {
        return mapper.entityToDto(repository.findByName(name)
                .orElseThrow(() -> new NothingFoundException("Partner", "name = " + name)));
    }

    @Override
    public PartnerDto findByRequisites(String requisites) {
        return mapper.entityToDto(repository.findByRequisites(requisites)
                .orElseThrow(() -> new NothingFoundException("Partner", "requisites = " + requisites)));
    }

    @Override
    public List<PartnerDto> findAllByAddressLike(String address) {
        List<PartnerDto> list = mapper.entityToDto(repository.findAllByAddressLike(address));
        if (list.isEmpty())
            throw new NothingFoundException("Partner", "address = " + address);
        return list;
    }

    @Override
    public List<PartnerDto> findAllByEmailLike(String email) {
        List<PartnerDto> list = mapper.entityToDto(repository.findAllByEmailLike(email));
        if (list.isEmpty())
            throw new NothingFoundException("Partner", "email = " + email);
        return list;
    }
}
