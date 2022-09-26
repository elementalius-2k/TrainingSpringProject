package com.example.trainingspringproject.services;

import javax.validation.Valid;
import java.util.List;

public interface BaseService<T> {
    void create(@Valid T dto);
    void update(@Valid T dto);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();
}
