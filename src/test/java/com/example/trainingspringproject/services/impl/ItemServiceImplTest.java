package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;
import com.example.trainingspringproject.models.entities.Item;
import com.example.trainingspringproject.models.mappers.ItemMapper;
import com.example.trainingspringproject.repositories.ItemRepository;
import com.example.trainingspringproject.services.ItemService;
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
class ItemServiceImplTest {
    private ItemService service;
    private Validator validator;

    @Mock
    private ItemRepository repositoryMock;
    @Mock
    private ItemMapper mapperMock;

    @BeforeEach
    void init() {
        service = new ItemServiceImpl(repositoryMock, mapperMock);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private final Long ID = 1L;
    private final Long INVOICE_ID = 1L;
    private final Long PRODUCT_ID = 1L;
    private final Integer QUANTITY = 1;
    private final Double PRICE = 1.0;

    @Test
    void create_thenSaveItem() {
        Item entity = new Item();
        ItemRequestDto dto = new ItemRequestDto(PRODUCT_ID, QUANTITY);

        doReturn(entity).when(mapperMock).dtoToEntity(dto, INVOICE_ID);

        service.create(dto, INVOICE_ID, PRICE);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void create_whenItemHasInvalidParameters_thenThrowException() {
        ItemRequestDto dto = new ItemRequestDto();

        Assertions.assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void delete_whenItemWithIdExists_thenDeleteItem() {
        Item entity = new Item();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.delete(ID);

        verify(repositoryMock, times(1)).delete(entity);
    }

    @Test
    void delete_whenItemWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findAllByInvoiceId_whenItemsWithInvoiceIdExist_thenReturnItems() {
        List<Item> entities = Collections.emptyList();
        List<ItemResponseDto> dtos = Collections.singletonList(new ItemResponseDto());

        doReturn(entities).when(repositoryMock).findAllByInvoiceId(INVOICE_ID);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByInvoiceId(INVOICE_ID), dtos);
    }

    @Test
    void findAllByInvoiceId_whenItemsWithInvoiceIdNotExist_thenThrowException() {
        List<Item> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByInvoiceId(INVOICE_ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByInvoiceId(INVOICE_ID));
    }
}