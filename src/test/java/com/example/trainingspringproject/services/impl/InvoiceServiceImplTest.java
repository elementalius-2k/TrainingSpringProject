package com.example.trainingspringproject.services.impl;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.models.entities.Invoice;
import com.example.trainingspringproject.models.enums.TransactionType;
import com.example.trainingspringproject.models.mappers.InvoiceMapper;
import com.example.trainingspringproject.repositories.InvoiceRepository;
import com.example.trainingspringproject.services.InvoiceService;
import com.example.trainingspringproject.services.ItemService;
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
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {
    private InvoiceService service;
    private Validator validator;

    @Mock
    private InvoiceRepository repositoryMock;
    @Mock
    private InvoiceMapper mapperMock;
    @Mock
    private ItemService itemServiceMock;
    @Mock
    private ProductService productServiceMock;

    @BeforeEach
    void init() {
        service = new InvoiceServiceImpl(repositoryMock, mapperMock, itemServiceMock, productServiceMock);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private final Long ID = 1L;
    private final Long PARTNER_ID = 2L;
    private final Long WORKER_ID = 3L;
    private final TransactionType TYPE_INCOME = TransactionType.INCOME;
    private final TransactionType TYPE_OUTCOME = TransactionType.OUTCOME;
    private final LocalDate DATE = LocalDate.now();
    private final Long ITEM_PRODUCT_ID = 4L;
    private final Integer ITEM_QUANTITY = 10;
    private final List<ItemRequestDto> ITEMS = List.of(new ItemRequestDto(ITEM_PRODUCT_ID, ITEM_QUANTITY));
    private final Double PRICE = 10.0;

    @Test
    void create_whenIncome_thenSaveInvoiceAndSaveItemsAndIncomeProducts() {
        Invoice entity = new Invoice();
        entity.setId(ID);
        InvoiceRequestDto dto = new InvoiceRequestDto(PARTNER_ID, WORKER_ID, TYPE_INCOME, ITEMS);
        ProductDto productDto = new ProductDto();
        productDto.setIncomePrice(PRICE);

        doReturn(entity).when(mapperMock).dtoToEntity(dto);
        doReturn(productDto).when(productServiceMock).findById(ITEM_PRODUCT_ID);

        service.create(dto);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
        verify(itemServiceMock, times(ITEMS.size())).create(any(ItemRequestDto.class), eq(ID), eq(PRICE));
        verify(productServiceMock, times(ITEMS.size())).income(ITEM_PRODUCT_ID, ITEM_QUANTITY);
    }

    @Test
    void create_whenOutcome_thenSaveInvoiceAndSaveItemsAndOutcomeProducts() {
        Invoice entity = new Invoice();
        entity.setId(ID);
        InvoiceRequestDto dto = new InvoiceRequestDto(PARTNER_ID, WORKER_ID, TYPE_OUTCOME, ITEMS);
        ProductDto productDto = new ProductDto();
        productDto.setOutcomePrice(PRICE);

        doReturn(entity).when(mapperMock).dtoToEntity(dto);
        doReturn(productDto).when(productServiceMock).findById(ITEM_PRODUCT_ID);

        service.create(dto);

        Assertions.assertTrue(validator.validate(dto).isEmpty());
        verify(repositoryMock, times(1)).save(entity);
        verify(itemServiceMock, times(ITEMS.size())).create(any(ItemRequestDto.class), eq(ID), eq(PRICE));
        verify(productServiceMock, times(ITEMS.size())).outcome(ITEM_PRODUCT_ID, ITEM_QUANTITY);
    }

    @Test
    void create_whenInvoiceHasInvalidParameters_thenValidationExceptionCreated() {
        InvoiceRequestDto dto = new InvoiceRequestDto();

        Assertions.assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void delete_whenInvoiceWithIdExists_thenDeleteInvoice() {
        Invoice entity = new Invoice();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);

        service.delete(ID);

        verify(repositoryMock, times(1)).delete(entity);
    }

    @Test
    void delete_whenInvoiceWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.delete(ID));
    }

    @Test
    void findById_whenInvoiceWithIdExists_thenReturnInvoice() {
        Invoice entity = new Invoice();
        InvoiceResponseDto dto = new InvoiceResponseDto();

        doReturn(Optional.of(entity)).when(repositoryMock).findById(ID);
        doReturn(dto).when(mapperMock).entityToDto(entity);

        Assertions.assertEquals(service.findById(ID), dto);
    }

    @Test
    void findById_whenInvoiceWithIdNotExist_thenThrowException() {
        doReturn(Optional.empty()).when(repositoryMock).findById(ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findById(ID));
    }

    @Test
    void findAll_whenInvoicesExist_thenReturnInvoices() {
        List<Invoice> entities = Collections.emptyList();
        List<InvoiceResponseDto> dtos = Collections.singletonList(new InvoiceResponseDto());

        doReturn(entities).when(repositoryMock).findAll();
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAll(), dtos);
    }

    @Test
    void findAllByPartnerId_whenInvoicesWithPartnerIdExist_thenReturnInvoices() {
        List<Invoice> entities = Collections.emptyList();
        List<InvoiceResponseDto> dtos = Collections.singletonList(new InvoiceResponseDto());

        doReturn(entities).when(repositoryMock).findAllByPartnerId(PARTNER_ID);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByPartnerId(PARTNER_ID), dtos);
    }

    @Test
    void findAllByPartnerId_whenInvoicesWithPartnerIdNotExist_thenThrowException() {
        List<Invoice> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByPartnerId(PARTNER_ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByPartnerId(PARTNER_ID));
    }

    @Test
    void findAllByWorkerId_whenInvoicesWithWorkerIdExist_thenReturnInvoices() {
        List<Invoice> entities = Collections.emptyList();
        List<InvoiceResponseDto> dtos = Collections.singletonList(new InvoiceResponseDto());

        doReturn(entities).when(repositoryMock).findAllByWorkerId(WORKER_ID);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByWorkerId(WORKER_ID), dtos);
    }

    @Test
    void findAllByWorkerId_whenInvoicesWithWorkerIdNotExist_thenThrowException() {
        List<Invoice> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByWorkerId(WORKER_ID);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByWorkerId(WORKER_ID));
    }

    @Test
    void findAllByType_whenInvoicesWithTypeExist_thenReturnInvoices() {
        List<Invoice> entities = Collections.emptyList();
        List<InvoiceResponseDto> dtos = Collections.singletonList(new InvoiceResponseDto());

        doReturn(entities).when(repositoryMock).findAllByType(TYPE_INCOME);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByType(TYPE_INCOME), dtos);
    }

    @Test
    void findAllByType_whenInvoicesWithTypeNotExist_thenThrowException() {
        List<Invoice> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByType(TYPE_INCOME);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByType(TYPE_INCOME));
    }

    @Test
    void findAllByDate_whenInvoicesWithDateExist_thenReturnInvoices() {
        List<Invoice> entities = Collections.emptyList();
        List<InvoiceResponseDto> dtos = Collections.singletonList(new InvoiceResponseDto());

        doReturn(entities).when(repositoryMock).findAllByDate(DATE);
        doReturn(dtos).when(mapperMock).entityToDto(entities);

        Assertions.assertEquals(service.findAllByDate(DATE), dtos);
    }

    @Test
    void findAllByDate_whenInvoicesWithDateNotExist_thenThrowException() {
        List<Invoice> entities = Collections.emptyList();

        doReturn(entities).when(repositoryMock).findAllByDate(DATE);

        Assertions.assertThrows(NothingFoundException.class, () -> service.findAllByDate(DATE));
    }
}