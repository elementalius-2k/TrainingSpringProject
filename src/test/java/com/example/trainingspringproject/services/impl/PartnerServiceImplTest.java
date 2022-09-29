package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.PartnerDto;
import com.example.trainingspringproject.models.entities.Partner;
import com.example.trainingspringproject.models.mappers.PartnerMapper;
import com.example.trainingspringproject.repositories.PartnerRepository;
import com.example.trainingspringproject.services.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class PartnerServiceImplTest {
    @Autowired
    private PartnerService service;

    @MockBean
    private PartnerRepository repositoryMock;
    @MockBean
    private PartnerMapper mapperMock;

    private final Long ID = 1L;
    private final String NAME = "Partner";
    private final String ADDRESS = "Address";
    private final String EMAIL = "mail@gmail.ru";
    private final String REQUISITES = "req";

    @Test
    void create_thenSavePartner() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(Optional.empty()).when(repositoryMock).findByRequisites(REQUISITES);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.create(dto);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void create_whenPartnerHasNonUniqueName_thenThrowException() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void create_whenPartnerHasNonUniqueRequisites_thenThrowException() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(Optional.of(entity)).when(repositoryMock).findByRequisites(REQUISITES);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void create_whenPartnerHasInvalidParameters_thenThrowException() {
        PartnerDto dto = new PartnerDto();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.create(dto));
    }

    @Test
    void update_thenUpdatePartner() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(Optional.empty()).when(repositoryMock).findByRequisites(REQUISITES);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.update(dto);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void update_whenPartnerHasInvalidParameters_thenThrowException() {
        PartnerDto dto = new PartnerDto();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.update(dto));
    }

    @Test
    void update_whenPartnerNotExist_thenThrowException() {
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.update(dto));
    }

    @Test
    void update_whenPartnerHasNonUniqueName_thenThrowException() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.update(dto));
    }

    @Test
    void update_whenPartnerHasNonUniqueRequisites_thenThrowException() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(Optional.of(entity)).when(repositoryMock).findByRequisites(REQUISITES);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.update(dto));
    }

    @Test
    void delete_whenPartnerWithIdExists_thenDeletePartner() {
        Partner entity = new Partner();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.delete(ID);

        verify(repositoryMock, times(1)).delete(entity);
    }

    @Test
    void delete_whenPartnerWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findById_whenPartnerWithIdExists_thenReturnPartner() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findById(ID), dto);
    }

    @Test
    void findById_whenPartnerWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findById(ID));
    }

    @Test
    void findAll_whenPartnersExist_thenReturnAllPartners() {
        List<Partner> entities = new ArrayList<>();
        List<PartnerDto> dtos = new ArrayList<>();
        dtos.add(new PartnerDto());

        doReturn(entities).when(repositoryMock).findAll();
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAll(), dtos);
    }

    @Test
    void findAll_whenNoPartnersExist_thenThrowException() {
        List<Partner> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAll();

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAll());
    }

    @Test
    void findByName_whenPartnerWithNameExist_thenReturnPartner() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findByName(NAME), dto);
    }

    @Test
    void findByName_whenPartnerWithNameNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findByName(NAME));
    }

    @Test
    void findByRequisites_whenPartnerWithRequisitesExist_thenReturnPartner() {
        Partner entity = new Partner();
        PartnerDto dto = new PartnerDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findByRequisites(REQUISITES);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findByRequisites(REQUISITES), dto);
    }

    @Test
    void findByRequisites_whenPartnerWithRequisitesNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findByRequisites(REQUISITES);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findByRequisites(REQUISITES));
    }

    @Test
    void findAllByAddressLike_whenPartnersWithAddressExist_thenReturnPartners() {
        List<Partner> entities = new ArrayList<>();
        List<PartnerDto> dtos = new ArrayList<>();
        dtos.add(new PartnerDto());

        doReturn(entities).when(repositoryMock).findAllByAddressLike(ADDRESS);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByAddressLike(ADDRESS), dtos);
    }

    @Test
    void findAllByAddressLike_whenPartnersWithAddressNotExist_thenThrowException() {
        List<Partner> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAllByAddressLike(ADDRESS);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByAddressLike(ADDRESS));
    }

    @Test
    void findAllByEmailLike_whenPartnersWithEmailExist_thenReturnPartners() {
        List<Partner> entities = new ArrayList<>();
        List<PartnerDto> dtos = new ArrayList<>();
        dtos.add(new PartnerDto());

        doReturn(entities).when(repositoryMock).findAllByEmailLike(EMAIL);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByEmailLike(EMAIL), dtos);
    }

    @Test
    void findAllByEmailLike_whenPartnersWithEmailNotExist_thenReturnPartners() {
        List<Partner> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAllByEmailLike(EMAIL);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByEmailLike(EMAIL));
    }
}