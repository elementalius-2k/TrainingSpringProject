package com.example.trainingspringproject.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "partner")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Partner {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "requisites", unique = true, nullable = false)
    private String requisites;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partner partner = (Partner) o;
        return Objects.equals(id, partner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
