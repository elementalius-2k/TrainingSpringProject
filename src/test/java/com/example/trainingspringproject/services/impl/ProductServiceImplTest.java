package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NotEnoughProductsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.models.entities.Product;
import com.example.trainingspringproject.models.mappers.ProductMapper;
import com.example.trainingspringproject.repositories.ProductRepository;
import com.example.trainingspringproject.services.ProductService;
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
class ProductServiceImplTest {
    private ProductService service;
    private Validator validator;
    @Mock
    private ProductRepository repositoryMock;
    @Mock
    private ProductMapper mapperMock;

    @BeforeEach
    void init () {
        service = new ProductServiceImpl(repositoryMock, mapperMock);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private final Long ID = 1L;
    private final String NAME = "Name";
    private final String DESCRIPTION = "Desc";
    private final String GROUP_NAME = "Group";
    private final String PRODUCER_NAME = "Producer";
    private final Integer QUANTITY = 10;
    private final Double INCOME_PRICE = 10.0;
    private final Double OUTCOME_PRICE = 20.0;
    private final Long PRODUCER_ID = 1L;
    private final Long GROUP_ID = 1L;
    private final Integer QUANTITY_CHANGE = 5;

    @Test
    void create_thenSaveProduct() {
        Product entity = new Product();
        ProductDto dto = new ProductDto(ID, NAME, DESCRIPTION, GROUP_NAME, PRODUCER_NAME,
                QUANTITY, INCOME_PRICE, OUTCOME_PRICE);

        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.create(dto);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void update_thenUpdateProduct() {
        Product entity = new Product();
        ProductDto dto = new ProductDto(ID, NAME, DESCRIPTION, GROUP_NAME, PRODUCER_NAME,
                QUANTITY, INCOME_PRICE, OUTCOME_PRICE);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(entity).when(mapperMock).dtoToEntity(dto);

        service.update(dto);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void update_whenProductNotExist_thenThrowException() {
        ProductDto dto = new ProductDto(ID, NAME, DESCRIPTION, GROUP_NAME, PRODUCER_NAME,
                QUANTITY, INCOME_PRICE, OUTCOME_PRICE);

        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        Assertions.assertThrows(NothingFoundException.class, () -> service.update(dto));
    }

    @Test
    void create_update_whenProductHasInvalidParameters_thenValidationExceptionCreated() {
        ProductDto dto = new ProductDto();

        Assertions.assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void delete_whenProductWithIdExist_thenDeleteProduct() {
        Product entity = new Product();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.delete(ID);

        verify(repositoryMock, times(1)).delete(entity);
    }

    @Test
    void delete_whenProductWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findById_whenProductWithIdExists_thenReturnProduct() {
        Product entity = new Product();
        ProductDto dto = new ProductDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findById(ID), dto);
    }

    @Test
    void findById_whenProductWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findById(ID));
    }

    @Test
    void findAll_whenProductsExist_thenReturnProducts() {
        List<Product> entities = Collections.emptyList();
        List<ProductDto> dtos = Collections.singletonList(new ProductDto());

        doReturn(entities).when(repositoryMock).findAll();
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAll(), dtos);
    }

    @Test
    void findAll_whenNoProductExist_thenThrowException() {
        List<Product> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAll();

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAll());
    }

    @Test
    void findAllByNameLike_whenProductsWithNameExist_thenReturnProducts() {
        List<Product> entities = Collections.emptyList();
        List<ProductDto> dtos = Collections.singletonList(new ProductDto());

        doReturn(entities).when(repositoryMock).findAllByNameLike(NAME);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByNameLike(NAME), dtos);
    }

    @Test
    void findAllByNameLike_whenProductsWithNameNotExist_thenThrowException() {
        List<Product> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByNameLike(NAME);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByNameLike(NAME));
    }

    @Test
    void findAllByProducerId_whenProductsWithProducerIdExists_thenReturnProducts() {
        List<Product> entities = Collections.emptyList();
        List<ProductDto> dtos = Collections.singletonList(new ProductDto());

        doReturn(entities).when(repositoryMock).findAllByProducerId(PRODUCER_ID);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByProducerId(PRODUCER_ID), dtos);
    }

    @Test
    void findAllByProducerId_whenProductsWithProducerIdNotExist_thenThrowException() {
        List<Product> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByProducerId(PRODUCER_ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByProducerId(PRODUCER_ID));
    }

    @Test
    void findAllByProductGroupId_whenProductsWithGroupIdExist_thenReturnProducts() {
        List<Product> entities = Collections.emptyList();
        List<ProductDto> dtos = Collections.singletonList(new ProductDto());

        doReturn(entities).when(repositoryMock).findAllByProductGroupId(GROUP_ID);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByProductGroupId(GROUP_ID), dtos);
    }

    @Test
    void findAllByProductGroupId_whenProductsWithGroupIdNotExist_thenThrowException() {
        List<Product> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByProductGroupId(GROUP_ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByProductGroupId(GROUP_ID));
    }

    @Test
    void income_thenIncreaseQuantityAndSaveProduct() {
        Product entity = new Product();
        entity.setQuantity(QUANTITY);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.income(ID, QUANTITY_CHANGE);

        Assertions.assertEquals(entity.getQuantity(), QUANTITY + QUANTITY_CHANGE);
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void income_whenProductWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.income(ID, QUANTITY_CHANGE));
    }

    @Test
    void outcome_thenDecreaseQuantityAndSaveProduct() {
        Product entity = new Product();
        entity.setQuantity(QUANTITY);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.outcome(ID, QUANTITY_CHANGE);

        Assertions.assertEquals(entity.getQuantity(), QUANTITY - QUANTITY_CHANGE);
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void outcome_whenProductWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.outcome(ID, QUANTITY_CHANGE));
    }

    @Test
    void outcome_whenProductQuantityIsNotEnough_thenThrowException() {
        Product entity = new Product();
        entity.setQuantity(QUANTITY);

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NotEnoughProductsException.class, () -> service.outcome(ID, QUANTITY * 2));
    }
}