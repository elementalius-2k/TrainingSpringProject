package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.enums.TransactionType;
import com.example.trainingspringproject.services.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
    private final InvoiceService service;

    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createInvoice(@Valid @RequestBody InvoiceRequestDto dto) {
        logger.info("Create invoice");
        service.create(dto);
    }

    @DeleteMapping("/delete")
    public void deleteInvoice(@RequestParam(name = "id") Long id) {
        logger.info("Delete invoice");
        service.delete(id);
    }

    @GetMapping("/find-by-id")
    public InvoiceResponseDto findInvoiceById(@RequestParam(name = "id") Long id) {
        logger.info("Get invoice by id");
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<InvoiceResponseDto> findAllInvoices() {
        logger.info("Get all invoices");
        return service.findAll();
    }

    @GetMapping("/find-by-partner-id")
    public List<InvoiceResponseDto> findInvoicesByPartnerId(@RequestParam(name = "partner_id") Long partnerId) {
        logger.info("Get invoices by partner id");
        return service.findAllByPartnerId(partnerId);
    }

    @GetMapping("/find-by-worker-id")
    public List<InvoiceResponseDto> findInvoicesByWorkerId(@RequestParam(name = "worker_id") Long workerId) {
        logger.info("Get invoices by worker id");
        return service.findAllByWorkerId(workerId);
    }

    @GetMapping("/find-by-type")
    public List<InvoiceResponseDto> findInvoicesByType(@RequestParam(name = "type") TransactionType type) {
        logger.info("Get invoices by type");
        return service.findAllByType(type);
    }

    @GetMapping("/find-by-date")
    public List<InvoiceResponseDto> findInvoicesByDate(@RequestParam(name = "date")LocalDate date) {
        logger.info("Get invoices by date");
        return service.findAllByDate(date);
    }
}
