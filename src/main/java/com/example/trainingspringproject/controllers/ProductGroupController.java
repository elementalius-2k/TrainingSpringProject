package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.models.dtos.ProductGroupDto;
import com.example.trainingspringproject.services.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class ProductGroupController {
    private final ProductGroupService service;

    Logger logger = LoggerFactory.getLogger(ProductGroupController.class);

    @PostMapping("/create")
    public void createProductGroup(@Valid @RequestBody ProductGroupDto dto) {
        logger.info("Create product group " + dto.toString());
        service.create(dto);
    }

    @PutMapping("/update")
    public void updateProductGroup(@Valid @RequestBody ProductGroupDto dto) {
        logger.info("Update product group " + dto.toString());
        service.update(dto);
    }

    @DeleteMapping("/delete")
    public void deleteProductGroup(@RequestParam(name = "id") Long id) {
        logger.info("Delete product group by id = " + id);
        service.delete(id);
    }

    @GetMapping("/find-by-id")
    public ProductGroupDto findProductGroupById(@RequestParam(name = "id") Long id) {
        logger.info("Get product group by id = " + id);
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<ProductGroupDto> findAllProductGroups() {
        logger.info("Get all product groups");
        return service.findAll();
    }

    @GetMapping("/find-by-name")
    public ProductGroupDto findProductGroupByName(@RequestParam(name = "name") String name) {
        logger.info("Get product group by name = " + name);
        return service.findByName(name);
    }
}
