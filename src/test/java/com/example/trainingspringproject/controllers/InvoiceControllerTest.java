package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.exceptions.NotEnoughProductsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.dtos.ItemRequestDto;
import com.example.trainingspringproject.models.enums.TransactionType;
import com.example.trainingspringproject.services.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc
class InvoiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvoiceService serviceMock;

    private final String COMMON_API = "/api/invoice";
    private final Long ID = 1L;
    private final Long PARTNER_ID = 2L;
    private final Long WORKER_ID = 3L;
    private final TransactionType TYPE = TransactionType.INCOME;
    private final List<ItemRequestDto> ITEMS = List.of(new ItemRequestDto());
    private final LocalDate DATE = LocalDate.parse("2022-09-29");

    @Test
    void createInvoice_thenCallServiceCreate() throws Exception {
        InvoiceRequestDto dto = new InvoiceRequestDto(PARTNER_ID, WORKER_ID, TYPE, ITEMS);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).create(dto);
    }

    @Test
    void createInvoice_whenInvoiceHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        InvoiceRequestDto dto = new InvoiceRequestDto();

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createInvoice_whenServiceThrowsNothingFoundException_thenStatusIsNotFound() throws Exception {
        InvoiceRequestDto dto = new InvoiceRequestDto(PARTNER_ID, WORKER_ID, TYPE, ITEMS);

        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).create(dto);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createInvoice_whenServiceThrowsNotEnoughProductsException_thenStatusIsNotFound() throws Exception {
        InvoiceRequestDto dto = new InvoiceRequestDto(PARTNER_ID, WORKER_ID, TYPE, ITEMS);

        doThrow(new NotEnoughProductsException(-1, -1, "Test")).when(serviceMock).create(dto);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteInvoice_thenCallServiceDelete() throws Exception {
        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).delete(ID);
    }

    @Test
    void deleteInvoice_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).delete(ID);

        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findInvoiceById_thenReturnInvoice() throws Exception {
        InvoiceResponseDto dto = new InvoiceResponseDto();

        doReturn(dto).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findInvoiceById_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllInvoices_thenReturnAllInvoices() throws Exception {
        List<InvoiceResponseDto> dtos = List.of(new InvoiceResponseDto());

        doReturn(dtos).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findAllInvoices_whenServiceThrowException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findInvoicesByPartnerId_thenReturnInvoices() throws Exception {
        List<InvoiceResponseDto> dtos = List.of(new InvoiceResponseDto());

        doReturn(dtos).when(serviceMock).findAllByPartnerId(PARTNER_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-partner-id?partner-id=" + PARTNER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findInvoicesByPartnerId_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByPartnerId(PARTNER_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-partner-id?partner-id=" + PARTNER_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findInvoicesByWorkerId_thenReturnInvoices() throws Exception {
        List<InvoiceResponseDto> dtos = List.of(new InvoiceResponseDto());

        doReturn(dtos).when(serviceMock).findAllByWorkerId(WORKER_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-worker-id?worker-id=" + WORKER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findInvoicesByWorkerId_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByWorkerId(WORKER_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-worker-id?worker-id=" + WORKER_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findInvoicesByType_thenReturnInvoices() throws Exception {
        List<InvoiceResponseDto> dtos = List.of(new InvoiceResponseDto());

        doReturn(dtos).when(serviceMock).findAllByType(TYPE);

        mockMvc.perform(get(COMMON_API + "/find-by-type?type=" + TYPE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findInvoicesByType_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByType(TYPE);

        mockMvc.perform(get(COMMON_API + "/find-by-type?type=" + TYPE))
                .andExpect(status().isNotFound());
    }

    @Test
    void findInvoicesByDate_thenReturnInvoices() throws Exception {
        List<InvoiceResponseDto> dtos = List.of(new InvoiceResponseDto());

        doReturn(dtos).when(serviceMock).findAllByDate(DATE);

        mockMvc.perform(get(COMMON_API + "/find-by-date?date=" + DATE.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findInvoicesByDate_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByDate(DATE);

        mockMvc.perform(get(COMMON_API + "/find-by-date?date=" + DATE.toString()))
                .andExpect(status().isNotFound());
    }
}