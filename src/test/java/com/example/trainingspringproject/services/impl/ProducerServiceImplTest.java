package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProducerDto;
import com.example.trainingspringproject.models.entities.Producer;
import com.example.trainingspringproject.models.mappers.ProducerMapper;
import com.example.trainingspringproject.repositories.ProducerRepository;
import com.example.trainingspringproject.services.ProducerService;
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
class ProducerServiceImplTest {
    @Autowired
    private ProducerService service;

    @MockBean
    private ProducerRepository repositoryMock;
    @MockBean
    private ProducerMapper mapperMock;

    private final Long ID = 1L;
    private final String NAME = "Producer";
    private final String ADDRESS = "Voronezh";

    @Test
    void create_thenSaveProducer() {
        Producer entity = new Producer();
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.create(dto);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void create_whenProducerHasNonUniqueName_thenThrowException() {
        Producer entity = new Producer();
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void create_whenProducerHasInvalidParameters_thenThrowException() {
        ProducerDto dto = new ProducerDto();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.create(dto));
    }

    @Test
    void update_thenUpdateProducer() {
        Producer entity = new Producer();
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.update(dto);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void update_whenProducerHasInvalidParameters_thenThrowException() {
        ProducerDto dto = new ProducerDto();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.update(dto));
    }

    @Test
    void update_whenProducerNotExist_thenThrowException() {
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.update(dto));
    }

    @Test
    void update_whenProducerHasNonUniqueName_thenThrowException() {
        Producer entity = new Producer();
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(AlreadyExistsException.class, () -> service.update(dto));
    }

    @Test
    void delete_whenProducerWithIdExists_thenDeleteProducer() {
        Producer entity = new Producer();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.delete(ID);

        verify(repositoryMock, times(1)).delete(entity);
    }

    @Test
    void delete_whenProducerWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findById_whenProducerWithIdExists_thenReturnProducer() {
        Producer entity = new Producer();
        ProducerDto dto = new ProducerDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findById(ID), dto);
    }

    @Test
    void findById_whenProducerWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findById(ID));
    }

    @Test
    void findAll_whenProducerExist_thenReturnAllProducers() {
        List<Producer> entities = new ArrayList<>();
        List<ProducerDto> dtos = new ArrayList<>();
        dtos.add(new ProducerDto());

        doReturn(entities).when(repositoryMock).findAll();
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAll(), dtos);
    }

    @Test
    void findAll_whenNoProducerExist_thenThrowException() {
        List<Producer> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAll();

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAll());
    }

    @Test
    void findByName_whenProducerWithNameExists_thenReturnProducer() {
        Producer entity = new Producer();
        ProducerDto dto = new ProducerDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findByName(NAME), dto);
    }

    @Test
    void findByName_whenProducerWithNameNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findByName(NAME));
    }

    @Test
    void findAllByAddressLike_whenProducersWithAddressExist_whenReturnProducers() {
        List<Producer> entities = new ArrayList<>();
        List<ProducerDto> dtos = new ArrayList<>();
        dtos.add(new ProducerDto());

        doReturn(entities).when(repositoryMock).findAllByAddressLike(ADDRESS);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByAddressLike(ADDRESS), dtos);
    }

    @Test
    void findAllByAddressLike_whenProducersWithAddressNotExits_thenThrowException() {
        List<Producer> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAllByAddressLike(ADDRESS);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByAddressLike(ADDRESS));
    }
}