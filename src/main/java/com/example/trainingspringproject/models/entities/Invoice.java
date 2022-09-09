package com.example.trainingspringproject.models.entities;

import com.example.trainingspringproject.models.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", partner=" + partner +
                ", worker=" + worker +
                ", type=" + type +
                ", date=" + date +
                '}';
    }
}
