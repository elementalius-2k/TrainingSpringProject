package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByInvoiceId(Long invoiceId);
}
