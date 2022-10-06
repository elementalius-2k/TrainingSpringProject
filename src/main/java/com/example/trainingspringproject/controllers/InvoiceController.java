package com.example.trainingspringproject.controllers;

import com.example.trainingspringproject.models.dtos.InvoiceRequestDto;
import com.example.trainingspringproject.models.dtos.InvoiceResponseDto;
import com.example.trainingspringproject.models.enums.TransactionType;
import com.example.trainingspringproject.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService service;

    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @PostMapping("/create")
    public void createInvoice(@Valid @RequestBody InvoiceRequestDto dto) {
        logger.info("Create invoice " + dto.toString());
        service.create(dto);
    }

    @DeleteMapping("/delete")
    public void deleteInvoice(@RequestParam(name = "id") Long id) {
        logger.info("Delete invoice by id = " + id);
        service.delete(id);
    }

    @GetMapping("/find-by-id")
    public InvoiceResponseDto findInvoiceById(@RequestParam(name = "id") Long id) {
        logger.info("Get invoice by id = " + id);
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<InvoiceResponseDto> findAllInvoices() {
        logger.info("Get all invoices");
        return service.findAll();
    }

    @GetMapping("/find-by-partner-id")
    public List<InvoiceResponseDto> findInvoicesByPartnerId(@RequestParam(name = "partner-id") Long partnerId) {
        logger.info("Get invoices by partner id = " + partnerId);
        return service.findAllByPartnerId(partnerId);
    }

    @GetMapping("/find-by-worker-id")
    public List<InvoiceResponseDto> findInvoicesByWorkerId(@RequestParam(name = "worker-id") Long workerId) {
        logger.info("Get invoices by worker id = " + workerId);
        return service.findAllByWorkerId(workerId);
    }

    @GetMapping("/find-by-type")
    public List<InvoiceResponseDto> findInvoicesByType(@RequestParam(name = "type") TransactionType type) {
        logger.info("Get invoices by type " + type);
        return service.findAllByType(type);
    }

    @GetMapping("/find-by-date")
    public List<InvoiceResponseDto> findInvoicesByDate(@RequestParam(name = "date")
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.info("Get invoices by date = " + date.toString());
        return service.findAllByDate(date);
    }
}
