package com.example.trainingspringproject.repositories;

import com.example.trainingspringproject.models.entities.Partner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends CrudRepository<Partner, Long> {
    Optional<Partner> findByName(String name);
    Optional<Partner> findByRequisites(String requisites);
    List<Partner> findAllByAddressLike(String address);
    List<Partner> findAllByEmailLike(String email);
}
