package com.petproject.currenciesproject.crud;
import java.util.List;
import java.util.Optional;

public interface CRUDInterface<T> {
    T readById(Long id);
    List<T> readAll();
    boolean create(T entity);
    void update(T entity);
    void delete(Long id);

}
