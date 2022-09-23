package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(String name);
    List<Producer> findAllByAddressLike(String address);
}
