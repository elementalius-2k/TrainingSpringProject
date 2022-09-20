package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Invoice;
import com.example.trainingspringproject.models.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<List<Invoice>> findAllByPartnerId(Long partnerId);
    Optional<List<Invoice>> findAllByWorkerId(Long workerId);
    Optional<List<Invoice>> findAllByType(TransactionType type);
    Optional<List<Invoice>> findAllByDate(LocalDate date);
}
