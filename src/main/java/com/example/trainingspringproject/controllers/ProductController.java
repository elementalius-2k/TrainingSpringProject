package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.models.dtos.ProductDto;
import com.example.trainingspringproject.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/create")
    public void createProduct(@Valid @RequestBody ProductDto dto) {
        logger.info("Create product " + dto.toString());
        service.create(dto);
    }

    @PutMapping("/update")
    public void updateProduct(@Valid @RequestBody ProductDto dto) {
        logger.info("Update product " + dto.toString());
        service.update(dto);
    }

    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam(name = "id") Long id) {
        logger.info("Delete product by id = " + id);
        service.delete(id);
    }

    @GetMapping("/find-by-id")
    public ProductDto findProductById(@RequestParam(name = "id") Long id) {
        logger.info("Get product by id = " + id);
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<ProductDto> findAllProducts() {
        logger.info("Get all products");
        return service.findAll();
    }

    @GetMapping("/find-by-name")
    public List<ProductDto> findProductsByName(@RequestParam(name = "name") String name) {
        logger.info("Get products by name = " + name);
        return service.findAllByNameLike(name);
    }

    @GetMapping("/find-by-producer-id")
    public List<ProductDto> findProductsByProducerId(@RequestParam(name = "producer-id") Long producerId) {
        logger.info("Get products by producer id = " + producerId);
        return service.findAllByProducerId(producerId);
    }

    @GetMapping("/find-by-group-id")
    public List<ProductDto> findProductsByGroupId(@RequestParam(name = "group-id") Long groupId) {
        logger.info("Get products by product group id = " + groupId);
        return service.findAllByProductGroupId(groupId);
    }
}
