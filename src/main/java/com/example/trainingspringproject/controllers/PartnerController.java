package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.models.dtos.PartnerDto;
import com.example.trainingspringproject.services.PartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/partner")
public class PartnerController {
    private final PartnerService service;

    Logger logger = LoggerFactory.getLogger(PartnerController.class);

    public PartnerController(PartnerService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createPartner(@Valid @RequestBody PartnerDto dto) {
        logger.info("Create partner " + dto.toString());
        service.create(dto);
    }

    @PutMapping("/update")
    public void updatePartner(@Valid @RequestBody PartnerDto dto) {
        logger.info("Update partner " + dto.toString());
        service.update(dto);
    }

    @DeleteMapping("/delete")
    public void deletePartner(@RequestParam(name = "id") Long id) {
        logger.info("Delete partner by id = " + id);
        service.delete(id);
    }

    @GetMapping("/find-by-id")
    public PartnerDto findPartnerById(@RequestParam(name = "id") Long id) {
        logger.info("Get partner by id = " + id);
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<PartnerDto> findAllPartners() {
        logger.info("Get all partners");
        return service.findAll();
    }

    @GetMapping("/find-by-name")
    public PartnerDto findPartnerByName(@RequestParam(name = "name") String name) {
        logger.info("Get partner by name = " + name);
        return service.findByName(name);
    }

    @GetMapping("/find-by-requisites")
    public PartnerDto findPartnerByRequisites(@RequestParam(name = "requisites") String requisites) {
        logger.info("Get partner by requisites = " + requisites);
        return service.findByRequisites(requisites);
    }

    @GetMapping("/find-by-address")
    public List<PartnerDto> findPartnersByAddress(@RequestParam(name = "address") String address) {
        logger.info("Get partners by address = " + address);
        return service.findAllByAddressLike(address);
    }

    @GetMapping("/find-by-email")
    public List<PartnerDto> findPartnersByEmail(@RequestParam(name = "email") String email) {
        logger.info("Get partners by email = " + email);
        return service.findAllByEmailLike(email);
    }
}
