package com.petproject.currenciesproject.crud;
import java.util.List;
import java.util.Optional;

public interface CRUDInterface<T> {
    T readById(Long id);
    T readByCode(String code);
    List<T> readAll();
    boolean create(T entity);

}
