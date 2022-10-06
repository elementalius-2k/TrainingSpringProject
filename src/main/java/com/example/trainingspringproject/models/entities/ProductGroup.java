package com.example.trainingspringproject.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "prod_group")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductGroup {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductGroup productGroup = (ProductGroup) o;
        return Objects.equals(id, productGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
