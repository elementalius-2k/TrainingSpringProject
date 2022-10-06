package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.PartnerDto;
import com.example.trainingspringproject.models.entities.Partner;
import com.example.trainingspringproject.models.mappers.PartnerMapper;
import com.example.trainingspringproject.repositories.PartnerRepository;
import com.example.trainingspringproject.services.PartnerService;
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
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository repository;
    private final PartnerMapper mapper;

    @Override
    @Transactional
    public void create(@Valid PartnerDto dto) {
        // проверка уникальности в базе
        checkName(dto.getName());
        checkRequisites(dto.getRequisites());

        Partner partner = mapper.dtoToEntity(dto);
        partner.setId(null);
        repository.save(partner);
    }

    @Override
    @Transactional
    public void update(@Valid PartnerDto dto) {
        //проверка существования записи с нужным id
        Partner oldData = getByIdOrElseThrow(dto.getId());
        //если произошло изменение поля name, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getName().equals(oldData.getName()))
            checkName(dto.getName());
        //если произошло изменение поля requisites, надо проверить, не нарушает ли новое значение уникальности в базе
        if (!dto.getRequisites().equals(oldData.getRequisites()))
            checkRequisites(dto.getRequisites());

        Partner newData = mapper.dtoToEntity(dto);
        newData.setId(oldData.getId());
        repository.save(newData);
    }

    @Override
    public void delete(Long id) {
        repository.delete(getByIdOrElseThrow(id));
    }

    @Override
    public PartnerDto findById(Long id) {
        return mapper.entityToDto(getByIdOrElseThrow(id));
    }

    @Override
    public List<PartnerDto> findAll() {
        List<Partner> list = (List<Partner>) repository.findAll();
        checkEmptyList(list, "all");
        return mapper.entityToDto(list);
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
        List<Partner> list = repository.findAllByAddressLike(address);
        checkEmptyList(list, "address = " + address);
        return mapper.entityToDto(list);
    }

    @Override
    public List<PartnerDto> findAllByEmailLike(String email) {
        List<Partner> list = repository.findAllByEmailLike(email);
        checkEmptyList(list, "email = " + email);
        return mapper.entityToDto(list);
    }

    private void checkName(String name) {
        if (repository.findByName(name).isPresent())
            throw new AlreadyExistsException("Partner with name " + name);
    }

    private void checkRequisites(String requisites) {
        if (repository.findByRequisites(requisites).isPresent())
            throw new AlreadyExistsException("Partner with requisites " + requisites);
    }

    private Partner getByIdOrElseThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NothingFoundException("Partner", "id = " + id));
    }

    private void checkEmptyList(List<Partner> list, String exceptionMessage) {
        if (CollectionUtils.isEmpty(list))
            throw new NothingFoundException("Partner", exceptionMessage);
    }
}
