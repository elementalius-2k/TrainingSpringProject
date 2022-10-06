package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProductGroupDto;
import com.example.trainingspringproject.models.entities.ProductGroup;
import com.example.trainingspringproject.models.mappers.ProductGroupMapper;
import com.example.trainingspringproject.repositories.ProductGroupRepository;
import com.example.trainingspringproject.services.ProductGroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductGroupServiceImplTest {
    private ProductGroupService service;
    private Validator validator;

    @Mock
    private ProductGroupRepository repositoryMock;
    @Mock
    private ProductGroupMapper mapperMock;

    @BeforeEach
    void init () {
        service = new ProductGroupServiceImpl(repositoryMock, mapperMock);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private final Long ID = 1L;
    private final String NAME = "group";

    @Test
    void create_thenSaveGroup() {
        ProductGroup entity = new ProductGroup();
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.create(dto);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void create_whenGroupHasNonUniqueName_thenThrowException() {
        ProductGroup entity = new ProductGroup();
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        Assertions.assertThrows(AlreadyExistsException.class, () -> service.create(dto));
    }

    @Test
    void update_thenUpdateGroup() {
        ProductGroup entity = new ProductGroup();
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.update(dto);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void update_whenGroupNotExist_thenThrowException() {
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        Assertions.assertThrows(NothingFoundException.class, () -> service.update(dto));
    }

    @Test
    void update_whenGroupHasNonUniqueName_thenThrowException() {
        ProductGroup entity = new ProductGroup();
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        Assertions.assertThrows(AlreadyExistsException.class, () -> service.update(dto));
    }

    @Test
    void create_update_whenGroupHasInvalidParameters_thenValidationExceptionCreated() {
        ProductGroupDto dto = new ProductGroupDto();

        Assertions.assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void delete_whenGroupWithIdExist_thenDeleteGroup() {
        ProductGroup entity = new ProductGroup();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.delete(ID);

        verify(repositoryMock, times(1)).delete(entity);
    }

    @Test
    void delete_whenGroupWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findById_whenGroupWithIdExist_thenReturnGroup() {
        ProductGroup entity = new ProductGroup();
        ProductGroupDto dto = new ProductGroupDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findById(ID), dto);
    }

    @Test
    void findById_whenGroupWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findById(ID));
    }

    @Test
    void findAll_whenGroupsExist_thenReturnAllGroups() {
        List<ProductGroup> entities = Collections.singletonList(new ProductGroup());
        List<ProductGroupDto> dtos = Collections.singletonList(new ProductGroupDto());

        doReturn(entities).when(repositoryMock).findAll();
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAll(), dtos);
    }

    @Test
    void findAll_whenNoGroupExist_thenThrowException() {
        List<ProductGroup> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAll();

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAll());
    }

    @Test
    void findByName_whenGroupWithNameExist_thenReturnGroup() {
        ProductGroup entity = new ProductGroup();
        ProductGroupDto dto = new ProductGroupDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findByName(NAME);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findByName(NAME), dto);
    }

    @Test
    void findByName_whenGroupWithNameNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findByName(NAME);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findByName(NAME));
    }
}