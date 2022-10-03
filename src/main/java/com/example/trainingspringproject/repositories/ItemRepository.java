package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findAllByInvoiceId(Long invoiceId);
}
