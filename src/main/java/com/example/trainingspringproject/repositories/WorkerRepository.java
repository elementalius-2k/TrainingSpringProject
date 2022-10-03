package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends CrudRepository<Worker, Long> {
    Optional<Worker> findByName(String name);
    List<Worker> findAllByJob(String job);
}
