package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.services.ProductService;
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

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService serviceMock;

    private final String COMMON_API = "/api/product";
    private final Long ID = 1L;
    private final String NAME = "Name";
    private final String DESCR = "Description";
    private final String GROUP_NAME = "Group";
    private final String PRODUCER_NAME = "Producer";
    private final Integer QUANTITY = 10;
    private final Double INCOME_PRICE = 10.0;
    private final Double OUTCOME_PRICE = 20.0;
    private final Long PRODUCER_ID = 2L;
    private final Long GROUP_ID = 3L;

    @Test
    void createProduct_thenCallServiceCreate() throws Exception {
        ProductDto dto = new ProductDto(ID, NAME, DESCR, GROUP_NAME, PRODUCER_NAME, QUANTITY, INCOME_PRICE, OUTCOME_PRICE);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).create(dto);
    }

    @Test
    void createProduct_whenProductHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        ProductDto dto = new ProductDto();

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct_thenCallServiceUpdate() throws Exception {
        ProductDto dto = new ProductDto(ID, NAME, DESCR, GROUP_NAME, PRODUCER_NAME, QUANTITY, INCOME_PRICE, OUTCOME_PRICE);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).update(dto);
    }

    @Test
    void updateProduct_whenProductHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        ProductDto dto = new ProductDto();

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        ProductDto dto = new ProductDto(ID, NAME, DESCR, GROUP_NAME, PRODUCER_NAME, QUANTITY, INCOME_PRICE, OUTCOME_PRICE);

        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_thenCallServiceDelete() throws Exception {
        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).delete(ID);
    }

    @Test
    void deleteProduct_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).delete(ID);

        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProductById_thenReturnProduct() throws Exception {
        ProductDto dto = new ProductDto();

        doReturn(dto).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findProductById_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllProducts_thenReturnAllProducts() throws Exception {
        List<ProductDto> dtos = List.of(new ProductDto());

        doReturn(dtos).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findAllProducts_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProductsByName_thenReturnProducts() throws Exception {
        List<ProductDto> dtos = List.of(new ProductDto());

        doReturn(dtos).when(serviceMock).findAllByNameLike(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findProductsByName_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByNameLike(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProductsByProducerId_thenReturnProducts() throws Exception {
        List<ProductDto> dtos = List.of(new ProductDto());

        doReturn(dtos).when(serviceMock).findAllByProducerId(PRODUCER_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-producer-id?producer-id=" + PRODUCER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findProductsByProducerId_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByProducerId(PRODUCER_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-producer-id?producer-id=" + PRODUCER_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findProductsByGroupId_thenReturnProducts() throws Exception {
        List<ProductDto> dtos = List.of(new ProductDto());

        doReturn(dtos).when(serviceMock).findAllByProductGroupId(GROUP_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-group-id?group-id=" + GROUP_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findProductsByGroupId_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByProductGroupId(GROUP_ID);

        mockMvc.perform(get(COMMON_API + "/find-by-group-id?group-id=" + GROUP_ID))
                .andExpect(status().isNotFound());
    }
}