package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ItemResponseDto;
import com.example.trainingspringproject.models.entities.Item;
import com.example.trainingspringproject.models.mappers.ItemMapper;
import com.example.trainingspringproject.repositories.ItemRepository;
import com.example.trainingspringproject.services.ItemService;
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
class ItemServiceImplTest {
    @Autowired
    private ItemService service;

    @MockBean
    private ItemRepository repositoryMock;
    @MockBean
    private ItemMapper mapperMock;

    private final Long ID = 1L;
    private final Long INVOICE_ID = 1L;
    private final Long PRODUCT_ID = 1L;
    private final Integer QUANTITY = 1;
    private final Double PRICE = 1.0;

    @Test
    void create_thenSaveItem() {
        Item entity = new Item();
        ItemRequestDto requestDto = new ItemRequestDto(PRODUCT_ID, QUANTITY);

        doReturn(entity).when(mapperMock).dtoToEntity(requestDto, INVOICE_ID);

        service.create(requestDto, INVOICE_ID, PRICE);

        verify(repositoryMock, times(1)).save(entity);
    }

    @Test
    void create_whenItemHasInvalidParameters_thenThrowException() {
        ItemRequestDto dto = new ItemRequestDto();

        Assertions.assertThrows(ConstraintViolationException.class, () -> service.create(dto, INVOICE_ID, PRICE));
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
        List<Item> entities = new ArrayList<>();
        List<ItemResponseDto> dtos = new ArrayList<>();
        dtos.add(new ItemResponseDto());

        doReturn(entities).when(repositoryMock).findAllByInvoiceId(INVOICE_ID);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByInvoiceId(INVOICE_ID), dtos);
    }

    @Test
    void findAllByInvoiceId_whenItemsWithInvoiceIdNotExist_thenThrowException() {
        List<Item> entities = new ArrayList<>();

        doReturn(entities).when(repositoryMock).findAllByInvoiceId(INVOICE_ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByInvoiceId(INVOICE_ID));
    }
}