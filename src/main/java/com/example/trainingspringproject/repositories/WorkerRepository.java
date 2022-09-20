package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository <Worker, Long> {
    Optional<Worker> findByName(String name);
    List<Worker> findAllByJob(String job);
}
