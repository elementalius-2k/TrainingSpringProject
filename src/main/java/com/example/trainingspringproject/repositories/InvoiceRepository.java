package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Invoice;
import com.example.trainingspringproject.models.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByPartnerId(Long partnerId);
    List<Invoice> findAllByWorkerId(Long workerId);
    List<Invoice> findAllByType(TransactionType type);
    List<Invoice> findAllByDate(LocalDate date);
}
