package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProductGroupDto;
import com.example.trainingspringproject.services.ProductGroupService;
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

@WebMvcTest(ProductGroupController.class)
@AutoConfigureMockMvc
class ProductGroupControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductGroupService serviceMock;

    private final String COMMON_API = "/api/group";
    private final Long ID = 1L;
    private final String NAME = "Name";

    @Test
    void createProductGroup_thenServiceCreate() throws Exception {
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).create(dto);
    }

    @Test
    void createProductGroup_whenGroupHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        ProductGroupDto dto = new ProductGroupDto();

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProductGroup_whenServiceThrowException_thenStatusIsConflict() throws Exception {
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).create(dto);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateProductGroup_thenCallServiceUpdate() throws Exception {
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).update(dto);
    }

    @Test
    void updateProductGroup_whenGroupHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        ProductGroupDto dto = new ProductGroupDto();

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductGroup_whenServiceThrowsNothingFoundException_thenStatusIsNotFound() throws Exception {
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProductGroup_whenServiceThrowAlreadyExistException_thenStatusIsConflict() throws Exception {
        ProductGroupDto dto = new ProductGroupDto(ID, NAME);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteProductGroup_thenCallServiceDelete() throws Exception {
        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).delete(ID);
    }

    @Test
    void deleteProductGroup_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).delete(ID);

        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProductGroupById_thenReturnGroup() throws Exception {
        ProductGroupDto dto = new ProductGroupDto();

        doReturn(dto).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findProductGroupById_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllProductGroups_thenReturnAllGroups() throws Exception {
        List<ProductGroupDto> dtos = List.of(new ProductGroupDto());

        doReturn(dtos).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findProductGroupByName_thenReturnGroup() throws Exception {
        ProductGroupDto dto = new ProductGroupDto();

        doReturn(dto).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findProductGroupByName_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isNotFound());
    }
}