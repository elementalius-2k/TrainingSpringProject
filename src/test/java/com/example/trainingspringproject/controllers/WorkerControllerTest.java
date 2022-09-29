package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.exceptions.AlreadyExistsException;
import com.example.trainingspringproject.exceptions.NothingFoundException;
import com.example.trainingspringproject.models.dtos.WorkerDto;
import com.example.trainingspringproject.services.WorkerService;
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

@WebMvcTest(WorkerController.class)
@AutoConfigureMockMvc
class WorkerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkerService serviceMock;

    private final String COMMON_API = "/api/worker";
    private final Long ID = 1L;
    private final String NAME = "Worker";
    private final String JOB = "Job";

    @Test
    void createWorker_thenCallServiceCreate() throws Exception {
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).create(dto);
    }

    @Test
    void createWorker_whenWorkerHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        WorkerDto dto = new WorkerDto();

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createWorker_whenServiceThrowException_thenStatusIsConflict() throws Exception {
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).create(dto);

        mockMvc.perform(post(COMMON_API + "/create")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void updateWorker_thenCallServiceUpdate() throws Exception {
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).update(dto);
    }

    @Test
    void updateWorker_whenWorkerHasInvalidParameters_thenStatusIsBadRequest() throws Exception {
        WorkerDto dto = new WorkerDto();

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWorker_whenServiceThrowsNothingFoundException_thenStatusIsNotFound() throws Exception {
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWorker_whenServiceThrowsAlreadyExistException_thenStatusIsConflict() throws Exception {
        WorkerDto dto = new WorkerDto(ID, NAME, JOB);

        doThrow(new AlreadyExistsException("Test")).when(serviceMock).update(dto);

        mockMvc.perform(put(COMMON_API + "/update")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteWorker_thenCallServiceDelete() throws Exception {
        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isOk());

        verify(serviceMock, times(1)).delete(ID);
    }

    @Test
    void deleteWorker_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).delete(ID);

        mockMvc.perform(delete(COMMON_API + "/delete?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findWorkerById_thenReturnWorker() throws Exception {
        WorkerDto dto = new WorkerDto();

        doReturn(dto).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findWorkerById_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findById(ID);

        mockMvc.perform(get(COMMON_API + "/find-by-id?id=" + ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllWorkers_thenReturnAllWorkers() throws Exception {
        List<WorkerDto> dtos = List.of(new WorkerDto());

        doReturn(dtos).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findAllWorkers_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAll();

        mockMvc.perform(get(COMMON_API + "/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findWorkerByName_thenReturnWorker() throws Exception {
        WorkerDto dto = new WorkerDto();

        doReturn(dto).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void findWorkerByName_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findByName(NAME);

        mockMvc.perform(get(COMMON_API + "/find-by-name?name=" + NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    void findWorkersByJob_thenReturnWorkers() throws Exception {
        List<WorkerDto> dtos = List.of(new WorkerDto());

        doReturn(dtos).when(serviceMock).findAllByJob(JOB);

        mockMvc.perform(get(COMMON_API + "/find-by-job?job=" + JOB))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtos)));
    }

    @Test
    void findWorkersByJob_whenServiceThrowsException_thenStatusIsNotFound() throws Exception {
        doThrow(new NothingFoundException("Test", "Test")).when(serviceMock).findAllByJob(JOB);

        mockMvc.perform(get(COMMON_API + "/find-by-job?job=" + JOB))
                .andExpect(status().isNotFound());
    }
}