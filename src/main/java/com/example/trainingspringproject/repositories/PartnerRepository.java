package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByName(String name);
    Optional<Partner> findByRequisites(String requisites);

    Optional<List<Partner>> findAllByAddressLike(String address);
    Optional<List<Partner>> findAllByEmailLike(String email);
}
