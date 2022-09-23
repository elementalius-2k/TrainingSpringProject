package com.example.trainingspringproject.services;

import com.example.trainingspringproject.models.dtos.PartnerDto;

import java.util.List;

public interface PartnerService extends BaseService<PartnerDto> {
    PartnerDto findByName(String name);
    PartnerDto findByRequisites(String requisites);
    List<PartnerDto> findAllByAddressLike(String address);
    List<PartnerDto> findAllByEmailLike(String email);
}
