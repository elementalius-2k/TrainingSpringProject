package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.PartnerDto;
import com.example.trainingspringproject.services.PartnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartnerController.class)
@AutoConfigureMockMvc
class PartnerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartnerService serviceMock;

    private final String COMMON_API = "/api/partner";
    private final Long ID = 1L;
    private final String NAME = "Name";
    private final String ADDRESS = "Address";
    private final String EMAIL = "email@mail.ru";
    private final String REQUISITES = "12345qwerty";

    @Test
    void createPartner_thenCallServiceCreate() throws Exception {
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).create(dto);
    }

    @Test
    void createPartner_whenPartnerHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        PartnerDto dto = new PartnerDto();

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPartner_whenServiceThrowsException_thenStatusIsConflict() throws Exception {
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).create(dto);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updatePartner_thenCallServiceUpdate() throws Exception {
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).update(dto);
    }

    @Test
    void updatePartner_whenPartnerHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        PartnerDto dto = new PartnerDto();

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePartner_whenServiceThrowsNothingFoundException_thenStatusIsNotFound() throws Exception {
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePartner_whenServiceThrowsAlreadyExistException_thenStatusIsConflict() throws Exception {
        PartnerDto dto = new PartnerDto(ID, NAME, ADDRESS, EMAIL, REQUISITES);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deletePartner_thenCallServiceDelete() throws Exception {
        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).delete(ID);
    }

    @Test
    void deletePartner_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).delete(ID);

        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findPartnerById_thenReturnPartner() throws Exception {
        PartnerDto dto = new PartnerDto();

        doReturn(dto).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findPartnerById_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllPartners_thenReturnPartners() throws Exception {
        List<PartnerDto> dtos = List.of(new PartnerDto());

        doReturn(dtos).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findAllPartners_whenServiceTrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findPartnerByName_thenReturnPartner() throws Exception {
        PartnerDto dto = new PartnerDto();

        doReturn(dto).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findPartnerByName_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void findPartnerByRequisites_thenReturnPartner() throws Exception {
        PartnerDto dto = new PartnerDto();

        doReturn(dto).when(serviceMock).findByRequisites(REQUISITES);

        mockMvc.perform(get(COMMON_API + "/find-by-requisites?requisites=" + REQUISITES))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findPartnerByRequisites_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findByRequisites(REQUISITES);

        mockMvc.perform(get(COMMON_API + "/find-by-requisites?requisites=" + REQUISITES))
                .andExpect(status().isNotFound());
    }

    @Test
    void findPartnersByAddress_thenReturnPartners() throws Exception {
        List<PartnerDto> dtos = List.of(new PartnerDto());

        doReturn(dtos).when(serviceMock).findAllByAddressLike(ADDRESS);

        mockMvc.perform(get(COMMON_API + "/find-by-address?address=" + ADDRESS))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findPartnersByAddress_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByAddressLike(ADDRESS);

        mockMvc.perform(get(COMMON_API + "/find-by-address?address=" + ADDRESS))
                .andExpect(status().isNotFound());
    }

    @Test
    void findPartnersByEmail_thenReturnPartners() throws Exception {
        List<PartnerDto> dtos = List.of(new PartnerDto());

        doReturn(dtos).when(serviceMock).findAllByEmailLike(EMAIL);

        mockMvc.perform(get(COMMON_API + "/find-by-email?email=" + EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findPartnersByEmail_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByEmailLike(EMAIL);

        mockMvc.perform(get(COMMON_API + "/find-by-email?email=" + EMAIL))
                .andExpect(status().isNotFound());
    }
}