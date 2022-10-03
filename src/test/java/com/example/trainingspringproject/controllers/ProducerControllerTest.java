package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProducerDto;
import com.example.trainingspringproject.services.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProducerController.class)
@AutoConfigureMockMvc
class ProducerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProducerService serviceMock;

    private final String COMMON_API = "/api/producer";
    private final Long ID = 1L;
    private final String NAME = "Name";
    private final String ADDRESS = "Address";

    @Test
    void createProducer_thenCallServiceCreate() throws Exception {
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).create(dto);
    }

    @Test
    void createProducer_whenProducerHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        ProducerDto dto = new ProducerDto();

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProducer_whenServiceThrowsException_thenStatusIsConflict() throws Exception {
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).create(dto);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateProducer_thenCallServiceUpdate() throws Exception {
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).update(dto);
    }

    @Test
    void updateProducer_whenProducerHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        ProducerDto dto = new ProducerDto();

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProducer_whenServiceThrowsNothingFoundException_thenStatusIsNotFound() throws Exception {
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProducer_whenServiceThrowsAlreadyExistException_thenStatusIsConflict() throws Exception {
        ProducerDto dto = new ProducerDto(ID, NAME, ADDRESS);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteProducer_thenCallServiceDelete() throws Exception {
        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).delete(ID);
    }

    @Test
    void deleteProducer_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).delete(ID);

        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProducerById_thenReturnProducer() throws Exception {
        ProducerDto dto = new ProducerDto();

        doReturn(dto).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findProducerById_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllProducers_thenReturnProducers() throws Exception {
        List<ProducerDto> dtos = Collections.singletonList(new ProducerDto());

        doReturn(dtos).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findAllProducers_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProducerByName_thenReturnProducer() throws Exception {
        ProducerDto dto = new ProducerDto();

        doReturn(dto).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findProducerByName_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProducersByAddress_thenReturnProducers() throws Exception {
        List<ProducerDto> dtos = Collections.singletonList(new ProducerDto());

        doReturn(dtos).when(serviceMock).findAllByAddressLike(ADDRESS);

        mockMvc.perform(get(COMMON_API + "/find-by-address?address=" + ADDRESS))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findProducersByAddress_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByAddressLike(ADDRESS);

        mockMvc.perform(get(COMMON_API + "/find-by-address?address=" + ADDRESS))
                .andExpect(status().isNotFound());
    }
}