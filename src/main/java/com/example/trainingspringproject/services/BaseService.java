package com.example.trainingspringproject.services;

import java.util.List;

public interface BaseService<T> {
    void create(T dto);
    void update(T dto);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();
}
