package com.example.trainingspringproject.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "worker")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Worker {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "job", nullable = false)
    private String job;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Objects.equals(id, worker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
