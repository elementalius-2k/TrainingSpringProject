package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.models.dtos.ProducerDto;
import com.example.trainingspringproject.services.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {
    private final ProducerService service;

    Logger logger = LoggerFactory.getLogger(ProducerController.class);

    public ProducerController(ProducerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createProducer(@Valid @RequestBody ProducerDto dto) {
        logger.info("Create producer");
        service.create(dto);
    }

    @PutMapping("/update")
    public void updateProducer(@Valid @RequestBody ProducerDto dto) {
        logger.info("Update producer");
        service.update(dto);
    }

    @DeleteMapping("/delete")
    public void deleteProducer(@RequestParam(name = "id") Long id) {
        logger.info("Delete producer");
        service.delete(id);
    }

    @GetMapping("/find-by-id")
    public ProducerDto findProducerById(@RequestParam(name = "id") Long id) {
        logger.info("Get producer by id");
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<ProducerDto> findAllProducers() {
        logger.info("Get all producers");
        return service.findAll();
    }

    @GetMapping("/find-by-name")
    public ProducerDto findProducerByName(@RequestParam(name = "name") String name) {
        logger.info("Get producer by name");
        return service.findByName(name);
    }

    @GetMapping("/find-by-address")
    public List<ProducerDto> findProducersByAddress(@RequestParam(name = "address") String address) {
        logger.info("Get producers by address");
        return service.findAllByAddressLike(address);
    }
}
