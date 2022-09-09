package com.example.trainingspringproject.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "product")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", nullable = false)
    private ProductGroup productGroup;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    @Min(0)
    private Integer quantity;

    @Column(name = "income_price", nullable = false)
    @Min(0)
    private Double incomePrice;

    @Column(name = "outcome_price", nullable = false)
    @Min(0)
    private Double outcomePrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productGroup=" + productGroup +
                ", producer=" + producer +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", incomePrice=" + incomePrice +
                ", outcomePrice=" + outcomePrice +
                '}';
    }
}
