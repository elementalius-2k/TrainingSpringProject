package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
