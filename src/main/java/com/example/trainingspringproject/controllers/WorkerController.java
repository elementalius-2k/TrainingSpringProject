package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.models.dtos.WorkerDto;
import com.example.trainingspringproject.services.WorkerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final WorkerService service;

    Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @PostMapping("/create")
    public void createWorker(@Valid @RequestBody WorkerDto dto) {
        logger.info("Create worker " + dto.toString());
        service.create(dto);
    }

    @PutMapping("/update")
    public void updateWorker(@Valid @RequestBody WorkerDto dto) {
        logger.info("Update worker " + dto.toString());
        service.update(dto);
    }

    @DeleteMapping("/delete")
    public void deleteWorker(@RequestParam(name = "id") Long id) {
        logger.info("Delete worker by id = " + id);
        service.delete(id);
    }

    @GetMapping("/find-by-id")
    public WorkerDto findWorkerById(@RequestParam(name = "id") Long id) {
        logger.info("Get worker by id = " + id);
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<WorkerDto> findAllWorkers() {
        logger.info("Get all workers");
        return service.findAll();
    }

    @GetMapping("/find-by-name")
    public WorkerDto findWorkerByName(@RequestParam(name = "name") String name) {
        logger.info("Get worker by name = " + name);
        return service.findByName(name);
    }

    @GetMapping("/find-by-job")
    public List<WorkerDto> findWorkersByJob(@RequestParam(name = "job") String job) {
        logger.info("Get workers by job = " + job);
        return service.findAllByJob(job);
    }
}
